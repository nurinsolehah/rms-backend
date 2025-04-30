package com.cmg.rms.rms_backend;

import com.cmg.rms.rms_backend.dto.RecipeDetailsDTO;
import com.cmg.rms.rms_backend.dto.RecipeListDTO;
import com.cmg.rms.rms_backend.dto.RecipeListRequestDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationRequestDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationResponseDTO;
import java.util.List;

public interface IRmsService {

  List<RecipeListDTO> getRecipeList(RecipeListRequestDTO requestDTO, PaginationRequestDTO pgDTO);

  PaginationResponseDTO getRecipeListPages(
      RecipeListRequestDTO requestDTO, PaginationRequestDTO pgDTO);

  RecipeDetailsDTO getRecipeDetails(Long recipeId);
}
