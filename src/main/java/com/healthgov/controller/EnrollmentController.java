package com.healthgov.controller;

import com.healthgov.dto.EnrollmentDTO;
import com.healthgov.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<EnrollmentDTO> getAll() {
        return service.getAllEnrollments();
    }

    @GetMapping("/{id}")
    public EnrollmentDTO getById(@PathVariable Long id) {
        return service.getEnrollmentById(id);
    }

    @PostMapping("/create")
    public EnrollmentDTO create(@RequestBody EnrollmentDTO dto) {
        return service.createEnrollment(dto);
    }

    @PutMapping("/{id}")
    public EnrollmentDTO update(@PathVariable Long id, @RequestBody EnrollmentDTO dto) {
        return service.updateEnrollment(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEnrollment(id);
    }
}