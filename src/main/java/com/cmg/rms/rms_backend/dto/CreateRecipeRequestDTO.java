package com.cmg.rms.rms_backend.dto;

import org.springframework.web.multipart.MultipartFile;

public record CreateRecipeRequestDTO(
    String recipeName, String description, String category, MultipartFile files) {}
