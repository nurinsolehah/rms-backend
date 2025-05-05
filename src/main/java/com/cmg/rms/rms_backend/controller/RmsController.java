package com.cmg.rms.rms_backend.controller;

import com.cmg.rms.rms_backend.IRmsService;
import com.cmg.rms.rms_backend.IRmsUsersService;
import com.cmg.rms.rms_backend.dto.CreateRecipeRequestDTO;
import com.cmg.rms.rms_backend.dto.FoodCategoryListDTO;
import com.cmg.rms.rms_backend.dto.RecipeDetailsDTO;
import com.cmg.rms.rms_backend.dto.RecipeListDTO;
import com.cmg.rms.rms_backend.dto.RecipeListRequestDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationRequestDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationResponseDTO;
import com.cmg.rms.rms_backend.security.UsersDTO;
import com.cmg.rms.rms_backend.util.LogUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/rms")
@Tag(name = "Recipe Management System", description = "API for Recipe Management System")
public class RmsController {

  private final IRmsService rmsService;
  private final IRmsUsersService rmsUsersService;

  @GetMapping("/recipe_list")
  /**
   * Retrieves a list of recipes based on the given parameters. The list is sorted by the given sort
   * column and direction, and is paginated by the given page and size. The recipe list is filtered
   * based on the given category and recipe name.
   *
   * @param category the category to filter by. If null, all categories are returned.
   * @param recipeName the recipe name to filter by. If null, all recipe names are returned.
   * @param sort the column to sort by. If null, the list is not sorted.
   * @param sortDirection the direction to sort by. If null, the list is sorted in ascending order.
   * @param page the page of the list to retrieve. If null, the first page is retrieved.
   * @param size the size of the list to retrieve. If null, the default size is used.
   * @return a list of recipes that match the given parameters.
   */
  public List<RecipeListDTO> getRecipeList(
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String recipeName,
      @RequestParam(required = false) String sort,
      @RequestParam(required = false) String sortDirection,
      @RequestParam(required = false) Long page,
      @RequestParam(required = false) Long size) {

    final String methodName = "getRecipeList";
    log.info(LogUtil.ENTRY, methodName);

    RecipeListRequestDTO requestDTO = new RecipeListRequestDTO(category, recipeName);
    PaginationRequestDTO pgDTO = new PaginationRequestDTO(sort, sortDirection, page, size);

    List<RecipeListDTO> response = rmsService.getRecipeList(requestDTO, pgDTO);

    log.info(LogUtil.EXIT, methodName);
    return response;
  }

  /**
   * Retrieves a pagination object containing the total number of pages and the current page.
   *
   * @param category the category to filter by. If null, all categories are returned.
   * @param recipeName the recipe name to filter by. If null, all recipe names are returned.
   * @param size the size of the list to retrieve. If null, the default size is used.
   * @return a pagination object containing the total number of pages and the current page.
   */
  @GetMapping("/recipe_list/page")
  public PaginationResponseDTO getRecipeListPages(
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String recipeName,
      @RequestParam(required = false) Long size) {
    final String methodName = "getRecipeListPage";
    log.info(LogUtil.ENTRY, methodName);

    RecipeListRequestDTO requestDTO = new RecipeListRequestDTO(category, recipeName);
    PaginationRequestDTO pgDTO = new PaginationRequestDTO(null, null, null, size);

    PaginationResponseDTO response = rmsService.getRecipeListPages(requestDTO, pgDTO);

    log.info(LogUtil.EXIT, methodName);
    return response;
  }

  /**
   * Retrieves a recipe by its id.
   *
   * @param recipeId the recipe id to retrieve.
   * @return the recipe details for the given recipe id.
   */
  @GetMapping("/recipe_list/{recipeId}")
  public RecipeDetailsDTO getRecipeDetail(@PathVariable("recipeId") Long recipeId) {
    final String methodName = "getRecipeDetail";
    log.info(LogUtil.ENTRY, methodName);

    RecipeDetailsDTO response = rmsService.getRecipeDetails(recipeId);

    log.info(LogUtil.EXIT, methodName);
    return response;
  }

  /**
   * Retrieves the image for a specific recipe by its ID and writes it to the HTTP response.
   *
   * @param recipeId the ID of the recipe whose image is to be retrieved.
   * @param responseImage the HTTP response where the image will be written.
   * @throws IOException if there is an error during reading or writing the image.
   */
  @GetMapping("/recipe_list/{recipeId}/image")
  public void getRecipeImage(
      @PathVariable("recipeId") Long recipeId, HttpServletResponse responseImage)
      throws IOException {
    final String methodName = "getRecipeImage";
    log.info(LogUtil.ENTRY, methodName);

    rmsService.getRecipeImage(recipeId, responseImage);

    log.info(LogUtil.EXIT, methodName);
  }

  /**
   * Adds a new recipe based on the given request parameters.
   *
   * @param requestDTO the request parameters containing the recipe details.
   */
  @PostMapping("/add_recipe")
  public void addRecipe(
      @RequestBody CreateRecipeRequestDTO requestDTO, Authentication authentication) {
    final String methodName = "addRecipe";
    log.info(LogUtil.ENTRY, methodName);

    UsersDTO user = rmsUsersService.getUserDetails();

    rmsService.addRecipe(requestDTO, user);

    log.info(LogUtil.EXIT, methodName);
  }

  /**
   * Updates the image of a recipe identified by its ID.
   *
   * @param recipeId the ID of the recipe whose image is to be updated.
   * @param image the new image file to be associated with the recipe; if null, no image update
   *     occurs.
   */
  @PostMapping("/update_image/{recipeId}")
  public void updateImage(
      @PathVariable("recipeId") Long recipeId,
      @RequestParam(required = false) MultipartFile image,
      Authentication authentication) {
    final String methodName = "updateImage";
    log.info(LogUtil.ENTRY, methodName);

    UsersDTO user = rmsUsersService.getUserDetails();

    rmsService.updateRecipeImage(recipeId, image, user);

    log.info(LogUtil.EXIT, methodName);
  }

  /**
   * Updates the details of a recipe identified by its ID.
   *
   * @param recipeId the ID of the recipe whose details are to be updated.
   * @param requestDTO the request parameters containing the recipe details to be updated.
   */
  @PostMapping("/update_recipe/{recipeId}")
  public void updateRecipeDetails(
      @PathVariable("recipeId") Long recipeId,
      @RequestBody CreateRecipeRequestDTO requestDTO,
      org.springframework.security.core.Authentication authentication) {
    final String methodName = "updateRecipeDetails";
    log.info(LogUtil.ENTRY, methodName);

    UsersDTO user = rmsUsersService.getUserDetails();

    rmsService.updateRecipeDetails(recipeId, requestDTO, user);

    log.info(LogUtil.EXIT, methodName);
  }

  /**
   * Deletes a recipe identified by its ID.
   *
   * @param recipeId the ID of the recipe to be deleted.
   */
  @PostMapping("/delete_recipe/{recipeId}")
  public void deleteRecipe(@PathVariable("recipeId") Long recipeId) {
    final String methodName = "deleteRecipe";
    log.info(LogUtil.ENTRY, methodName);

    rmsService.deleteRecipe(recipeId);

    log.info(LogUtil.EXIT, methodName);
  }

  /**
   * @return a list of food categories as {@link FoodCategoryListDTO}s. Each {@link
   *     FoodCategoryListDTO} contains the category code as the value and the category name as the
   *     description and the active flag as the status.
   */
  @GetMapping("/food_category")
  public List<FoodCategoryListDTO> getFoodCategory() {
    final String methodName = "getFoodCategory";
    log.info(LogUtil.ENTRY, methodName);

    List<FoodCategoryListDTO> response = rmsService.getFoodCategory();

    log.info(LogUtil.EXIT, methodName);
    return response;
  }

  /**
   * Removes a food category by its category code by setting its active flag to inactive.
   *
   * @param categoryCode the code of the category to be removed; if null, no operation is performed
   */
  @PostMapping("/food_category/remove")
  public void removeFoodCategory(
      @RequestParam(required = false) Long categoryId, Authentication authentication) {
    final String methodName = "removeFoodCategory";
    log.info(LogUtil.ENTRY, methodName);

    UsersDTO user = rmsUsersService.getUserDetails();

    rmsService.removeFoodCategory(categoryId, user);

    log.info(LogUtil.EXIT, methodName);
  }
}
