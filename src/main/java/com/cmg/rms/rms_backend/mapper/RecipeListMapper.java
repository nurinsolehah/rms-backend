package com.cmg.rms.rms_backend.mapper;

public class RecipeListMapper extends ColumnMapper {
  public RecipeListMapper() {
    COLUMN_MAP.put("recipeId", "recipeId");
    COLUMN_MAP.put("recipeName", "recipeName");
    COLUMN_MAP.put("description", "description");
    COLUMN_MAP.put("category", "category");
  }
}
