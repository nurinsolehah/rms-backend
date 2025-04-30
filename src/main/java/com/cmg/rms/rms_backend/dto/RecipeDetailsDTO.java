package com.cmg.rms.rms_backend.dto;

public record RecipeDetailsDTO(
    String recipeName,
    String description,
    String category,
    String image,
    String recipeIngredients,
    String recipeInstructions) {}
