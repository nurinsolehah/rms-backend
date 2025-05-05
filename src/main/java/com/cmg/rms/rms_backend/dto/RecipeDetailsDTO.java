package com.cmg.rms.rms_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDetailsDTO {
  String recipeName;
  String description;
  String category;
  // byte[] image;
  String recipeIngredients;
  String recipeInstructions;
}
