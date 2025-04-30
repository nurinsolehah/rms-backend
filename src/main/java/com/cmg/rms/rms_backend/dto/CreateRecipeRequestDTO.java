package com.cmg.rms.rms_backend.dto;

public record CreateRecipeRequestDTO(
    String recipeName, String description, String category, String ingredients, String instructions
    // List<RecipeInstructionsDTO> recipeInstructions
    ) {}
