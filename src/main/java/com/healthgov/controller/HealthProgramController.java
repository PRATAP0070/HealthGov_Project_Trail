package com.healthgov.controller;

import com.healthgov.dto.HealthProgramDTO;
import com.healthgov.service.HealthProgramService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class HealthProgramController {

    private final HealthProgramService service;

    public HealthProgramController(HealthProgramService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<HealthProgramDTO> getAll() {
        return service.getAllPrograms();
    }

    @GetMapping("/{id}")
    public HealthProgramDTO getById(@PathVariable Long id) {
        return service.getProgramById(id);
    }

    @PostMapping("/create")
    public HealthProgramDTO create(@RequestBody HealthProgramDTO dto) {
        return service.createProgram(dto);
    }

    @PutMapping("/{id}")
    public HealthProgramDTO update(@PathVariable Long id, @RequestBody HealthProgramDTO dto) {
        return service.updateProgram(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteProgram(id);
    }
    
}