package com.cmg.rms.rms_backend;

import com.cmg.rms.rms_backend.dto.CreateRecipeRequestDTO;
import com.cmg.rms.rms_backend.dto.FoodCategoryListDTO;
import com.cmg.rms.rms_backend.dto.RecipeDetailsDTO;
import com.cmg.rms.rms_backend.dto.RecipeListDTO;
import com.cmg.rms.rms_backend.dto.RecipeListRequestDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationRequestDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationResponseDTO;
import com.cmg.rms.rms_backend.security.UsersDTO;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IRmsService {

  List<RecipeListDTO> getRecipeList(RecipeListRequestDTO requestDTO, PaginationRequestDTO pgDTO);

  PaginationResponseDTO getRecipeListPages(
      RecipeListRequestDTO requestDTO, PaginationRequestDTO pgDTO);

  RecipeDetailsDTO getRecipeDetails(Long recipeId);

  void getRecipeImage(Long recipeId, HttpServletResponse responseImage) throws IOException;

  void addRecipe(CreateRecipeRequestDTO requestDTO, UsersDTO user);

  void updateRecipeImage(Long recipeId, MultipartFile image, UsersDTO user);

  void updateRecipeDetails(Long recipeId, CreateRecipeRequestDTO requestDTO, UsersDTO user);

  void deleteRecipe(Long recipeId);

  List<FoodCategoryListDTO> getFoodCategory(String categoryName, PaginationRequestDTO pgDTO);

  PaginationResponseDTO getFoodCategoryListPages(String categoryName, PaginationRequestDTO pgDTO);

  void removeFoodCategory(Long categoryId, UsersDTO user);
}
