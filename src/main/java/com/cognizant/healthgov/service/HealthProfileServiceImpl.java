package com.cognizant.healthgov.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.healthgov.dto.HealthProfileResponseDTO;
import com.cognizant.healthgov.exceptions.CitizenNotFoundException;
import com.cognizant.healthgov.exceptions.HealthProfileNotFoundException;
import com.cognizant.healthgov.model.Citizen;
import com.cognizant.healthgov.model.HealthProfile;
import com.cognizant.healthgov.repository.CitizenRepository;
import com.cognizant.healthgov.repository.HealthProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HealthProfileServiceImpl implements HealthProfileService {

    private final HealthProfileRepository profileRepo;
    private final CitizenRepository citizenRepo;

    @Override
    public HealthProfileResponseDTO saveOrUpdateProfile(Long citizenId, HealthProfile input) {
        Optional<Citizen> optCitizen = citizenRepo.findById(citizenId);
        if (optCitizen.isEmpty()) {
            throw new CitizenNotFoundException(citizenId);
        }
        Citizen citizen = optCitizen.get();

        HealthProfile profile = profileRepo.findByCitizen(citizen);
        if (profile == null) {
            profile = new HealthProfile();
            profile.setCitizen(citizen);
        }

        profile.setMedicalHistoryNote(input.getMedicalHistoryNote());
        profile.setAllergies(input.getAllergies());
        profile.setStatus(input.getStatus());

        HealthProfile saved = profileRepo.save(profile);
        return mapToDTO(saved);
    }

    @Override
    public HealthProfileResponseDTO getProfile(Long citizenId) {
        Optional<Citizen> optCitizen = citizenRepo.findById(citizenId);
        if (optCitizen.isEmpty()) {
            throw new CitizenNotFoundException(citizenId);
        }
        Citizen citizen = optCitizen.get();

        HealthProfile profile = profileRepo.findByCitizen(citizen);
        if (profile == null) {
            throw new HealthProfileNotFoundException(citizenId);
        }

        return mapToDTO(profile);
    }

    @Override
    public void deleteProfile(Long citizenId) {
        Optional<Citizen> optCitizen = citizenRepo.findById(citizenId);
        if (optCitizen.isEmpty()) {
            throw new CitizenNotFoundException(citizenId);
        }
        Citizen citizen = optCitizen.get();

        HealthProfile profile = profileRepo.findByCitizen(citizen);
        if (profile == null) {
            throw new HealthProfileNotFoundException(citizenId);
        }

        profileRepo.delete(profile);
    }

    private HealthProfileResponseDTO mapToDTO(HealthProfile hp) {
        String statusName = (hp.getStatus() != null) ? hp.getStatus().name() : "";
        
        return new HealthProfileResponseDTO(
            hp.getHealthProfileId(),
            hp.getCitizen().getCitizenId(),
            hp.getMedicalHistoryNote(),
            hp.getAllergies(),
            statusName
        );
    }
}