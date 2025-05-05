package com.cmg.rms.rms_backend.dto;

public record RmsRegistrationDTO(
    String username, String password, String userRole, String contactNo) {}
