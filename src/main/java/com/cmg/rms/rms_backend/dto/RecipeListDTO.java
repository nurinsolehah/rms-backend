package com.cmg.rms.rms_backend.dto;

public record RecipeListDTO(
    Long recipeSeqno, String recipeName, String description, String category) {}
