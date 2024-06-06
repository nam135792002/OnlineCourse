package com.springboot.courses.controller;

import com.springboot.courses.service.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/certificate")
@Tag(
        name = "Certificate Controller",
        description = "REST APIs related to Certificate Entity"
)
public class CertificateController {

    @Autowired private CertificateService certificateService;

    @Operation(
            summary = "Get Certificate by ID REST API",
            description = "This REST API is used to get a certificate by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Certificate retrieved successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Certificate not found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Integer certificateId){
        return ResponseEntity.ok(certificateService.getById(certificateId));
    }
}
