package com.healthgov.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.healthgov.dto.HealthProgramDTO;
import com.healthgov.exceptions.ProgramException;
import com.healthgov.model.HealthProgram;
import com.healthgov.repository.HealthProgramRepository;

@Service
public class HealthProgramServiceImpl implements HealthProgramService {

    private final HealthProgramRepository repo;

    public HealthProgramServiceImpl(HealthProgramRepository repo) {
        this.repo = repo;
    }

    // -------- Manual mapping helpers --------
    private HealthProgramDTO toDto(HealthProgram e) {
        if (e == null) return null;
        HealthProgramDTO d = new HealthProgramDTO();
        d.setProgramId(e.getProgramId());
        d.setTitle(e.getTitle());
        d.setDescription(e.getDescription());
        d.setStartDate(e.getStartDate());
        d.setEndDate(e.getEndDate());
        d.setBudget(e.getBudget());
        d.setStatus(e.getStatus());
        return d;
    }

    private void copyToEntity(HealthProgramDTO d, HealthProgram e) {
        if (d.getTitle() != null) e.setTitle(d.getTitle());
        if (d.getDescription() != null) e.setDescription(d.getDescription());
        if (d.getStartDate() != null) e.setStartDate(d.getStartDate());
        if (d.getEndDate() != null) e.setEndDate(d.getEndDate());
        if (d.getBudget() != null) e.setBudget(d.getBudget());
        if (d.getStatus() != null) e.setStatus(d.getStatus());
    }

    // ----------------------------------------

    @Override
    public List<HealthProgramDTO> getAllPrograms() {
        List<HealthProgramDTO> out = new ArrayList<>();
        for (HealthProgram hp : repo.findAll()) {
            out.add(toDto(hp));
        }
        return out;
    }

    @Override
    public HealthProgramDTO getProgramById(Long id) {
        HealthProgram e = repo.findById(id)
                .orElseThrow(() -> ProgramException.notFound("Program ID " + id + " not found"));
        return toDto(e);
    }

    @Override
    public HealthProgramDTO createProgram(HealthProgramDTO dto) {
        HealthProgram e = new HealthProgram();
        // ID is auto; ignore any dto.programId
        copyToEntity(dto, e);
        return toDto(repo.save(e));
    }

    @Override
    public HealthProgramDTO updateProgram(Long id, HealthProgramDTO dto) {
        HealthProgram existing = repo.findById(id)
                .orElseThrow(() -> ProgramException.notFound("Program ID " + id + " not found"));
        copyToEntity(dto, existing);
        return toDto(repo.save(existing));
    }

    @Override
    public void deleteProgram(Long id) {
        if (!repo.existsById(id)) throw ProgramException.notFound("Program ID " + id + " not found");
        repo.deleteById(id);
    }
}