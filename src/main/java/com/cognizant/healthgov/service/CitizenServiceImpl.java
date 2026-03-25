package com.cognizant.healthgov.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.healthgov.dto.CitizenRequestDTO;
import com.cognizant.healthgov.dto.CitizenResponseDTO;
import com.cognizant.healthgov.exceptions.CitizenNotFoundException;
import com.cognizant.healthgov.model.Citizen;
import com.cognizant.healthgov.model.Gender;
import com.cognizant.healthgov.model.RegistrationStatus;
import com.cognizant.healthgov.repository.CitizenRepository;

@Service
@Transactional
public class CitizenServiceImpl implements CitizenService {

    private final CitizenRepository repository;

    public CitizenServiceImpl(CitizenRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CitizenResponseDTO> getAllCitizens() {
        List<Citizen> allCitizens = repository.findAll();
        List<CitizenResponseDTO> responseList = new ArrayList<>();
        for (Citizen citizen : allCitizens) {
            responseList.add(mapToResponse(citizen));
        }
        return responseList;
    }

    @Override
    public CitizenResponseDTO getCitizen(Long id) {
        Optional<Citizen> opt = repository.findById(id);
        if (opt.isPresent()) {
            return mapToResponse(opt.get());
        } else {
            throw new CitizenNotFoundException("Citizen ID " + id + " not found");
        }
    }

    @Override
    public CitizenResponseDTO registerCitizen(CitizenRequestDTO request) {
        if (repository.existsByContactInfo(request.contactInfo())) {
            throw new RuntimeException("Contact info already exists");
        }
        Citizen citizen = new Citizen();
        citizen.setName(request.name());
        citizen.setDob(request.dob());
        citizen.setGender(Gender.valueOf(request.gender().toUpperCase()));
        citizen.setAddress(request.address());
        citizen.setContactInfo(request.contactInfo());
        citizen.setStatus(RegistrationStatus.PENDING);

        return mapToResponse(repository.save(citizen));
    }

    @Override
    public CitizenResponseDTO updateCitizen(Long id, CitizenRequestDTO request) {
        Optional<Citizen> citizenOpt = repository.findById(id);
        if (citizenOpt.isPresent()) {
            Citizen citizen = citizenOpt.get();
            citizen.setName(request.name());
            citizen.setAddress(request.address());
            citizen.setContactInfo(request.contactInfo());
            
            Citizen updated = repository.save(citizen);
            return mapToResponse(updated);
        } else {
            throw new CitizenNotFoundException("Citizen not found for update with ID: " + id);
        }
    }

    @Override
    public void deleteCitizen(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new CitizenNotFoundException("Citizen not found for deletion with ID: " + id);
        }
    }

    private CitizenResponseDTO mapToResponse(Citizen c) {
        return new CitizenResponseDTO(c.getCitizenId(), c.getName(), c.getDob(), 
                                     c.getGender().name(), c.getAddress(), c.getStatus().name());
    }
}