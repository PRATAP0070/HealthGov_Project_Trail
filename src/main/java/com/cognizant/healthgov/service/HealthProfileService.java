package com.cognizant.healthgov.service;

import com.cognizant.healthgov.dto.HealthProfileResponseDTO;
import com.cognizant.healthgov.model.HealthProfile;

public interface HealthProfileService {
    HealthProfileResponseDTO saveOrUpdateProfile(Long citizenId, HealthProfile input);
    HealthProfileResponseDTO getProfile(Long citizenId);
    void deleteProfile(Long citizenId);
}