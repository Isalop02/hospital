package co.edu.umanizales.hospital.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Main API Controller for Hospital Management System
 */
@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Hospital Management API");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", "Hospital Management System");
        response.put("description", "REST API for managing hospital operations");
        response.put("endpoints", new String[]{
            "/api/patients",
            "/api/doctors",
            "/api/nurses",
            "/api/specialties",
            "/api/appointments",
            "/api/medicines",
            "/api/treatments",
            "/api/medical-records",
            "/api/rooms",
            "/api/invoices"
        });
        return ResponseEntity.ok(response);
    }
}
