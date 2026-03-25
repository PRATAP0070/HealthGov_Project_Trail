package com.healthgov.service;

import com.healthgov.dto.HealthProgramDTO;
import java.util.List;

public interface HealthProgramService {
    List<HealthProgramDTO> getAllPrograms();
    HealthProgramDTO getProgramById(Long id);
    HealthProgramDTO createProgram(HealthProgramDTO dto);
    HealthProgramDTO updateProgram(Long id, HealthProgramDTO dto);
    void deleteProgram(Long id);
}