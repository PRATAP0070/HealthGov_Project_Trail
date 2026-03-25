package com.cognizant.healthgov.service;

import java.util.List;
import com.cognizant.healthgov.dto.CitizenRequestDTO;
import com.cognizant.healthgov.dto.CitizenResponseDTO;

public interface CitizenService {
    List<CitizenResponseDTO> getAllCitizens();
    CitizenResponseDTO getCitizen(Long id);
    CitizenResponseDTO registerCitizen(CitizenRequestDTO request);
    CitizenResponseDTO updateCitizen(Long id, CitizenRequestDTO request);
    void deleteCitizen(Long id);
}