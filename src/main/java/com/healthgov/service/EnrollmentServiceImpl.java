package com.healthgov.service;

import com.healthgov.dto.EnrollmentDTO;
import com.healthgov.exceptions.ProgramException;
import com.healthgov.model.Citizen;
import com.healthgov.model.Enrollment;
import com.healthgov.model.HealthProgram;
import com.healthgov.repository.EnrollmentRepository;
import com.healthgov.repository.HealthProgramRepository;
import com.healthgov.service.EnrollmentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository repo;
    private final HealthProgramRepository programRepo;

    public EnrollmentServiceImpl(EnrollmentRepository repo, HealthProgramRepository programRepo) {
        this.repo = repo;
        this.programRepo = programRepo;
    }

    // -------- Manual mapping helpers --------
    private EnrollmentDTO toDto(Enrollment e) {
        if (e == null) return null;
        EnrollmentDTO d = new EnrollmentDTO();
        d.setEnrollmentId(e.getEnrollmentId());
        d.setDate(e.getDate());
        d.setStatus(e.getStatus());
        d.setCitizenId(e.getCitizen() != null ? e.getCitizen().getCitizenId() : null);
        d.setProgramId(e.getProgram() != null ? e.getProgram().getProgramId() : null);
        return d;
    }

    private void copyToEntity(EnrollmentDTO d, Enrollment e) {
        if (d.getDate() != null) e.setDate(d.getDate());
        if (d.getStatus() != null) e.setStatus(d.getStatus());

        if (d.getCitizenId() != null) {
            Citizen c = new Citizen();
            c.setCitizenId(d.getCitizenId());
            e.setCitizen(c);
        }

        if (d.getProgramId() != null) {
            // ensure Program exists
            HealthProgram p = programRepo.findById(d.getProgramId())
                    .orElseThrow(() -> ProgramException.notFound("Program ID " + d.getProgramId() + " not found"));
            e.setProgram(p);
        }
    }
    // ----------------------------------------

    @Override
    public List<EnrollmentDTO> getAllEnrollments() {
        List<EnrollmentDTO> out = new ArrayList<>();
        for (Enrollment e : repo.findAll()) {
            out.add(toDto(e));
        }
        return out;
    }

    @Override
    public EnrollmentDTO getEnrollmentById(Long id) {
        Enrollment e = repo.findById(id)
                .orElseThrow(() -> ProgramException.notFound("Enrollment ID " + id + " not found"));
        return toDto(e);
    }

    @Override
    public EnrollmentDTO createEnrollment(EnrollmentDTO dto) {
        if (dto.getProgramId() == null) throw ProgramException.badRequest("programId is required");
        if (dto.getCitizenId() == null) throw ProgramException.badRequest("citizenId is required");

        Enrollment e = new Enrollment();
        // ID is auto; ignore any dto.enrollmentId
        copyToEntity(dto, e);

        return toDto(repo.save(e));
    }

    @Override
    public EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO dto) {
        Enrollment existing = repo.findById(id)
                .orElseThrow(() -> ProgramException.notFound("Enrollment ID " + id + " not found"));
        copyToEntity(dto, existing);
        return toDto(repo.save(existing));
    }

    @Override
    public void deleteEnrollment(Long id) {
        if (!repo.existsById(id)) throw ProgramException.notFound("Enrollment ID " + id + " not found");
        repo.deleteById(id);
    }
}