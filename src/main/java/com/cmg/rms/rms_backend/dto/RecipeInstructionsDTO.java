package com.cmg.rms.rms_backend.dto;

import java.math.BigDecimal;

public record RecipeInstructionsDTO(
    String recipeInstructions,
    String recipeIngredients,
    BigDecimal quantity,
    String quantityUnit) {}
