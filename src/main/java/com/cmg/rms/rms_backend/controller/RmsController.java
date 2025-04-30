package com.cmg.rms.rms_backend.controller;

import com.cmg.rms.rms_backend.IRmsService;
import com.cmg.rms.rms_backend.dto.RecipeDetailsDTO;
import com.cmg.rms.rms_backend.dto.RecipeListDTO;
import com.cmg.rms.rms_backend.dto.RecipeListRequestDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationRequestDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationResponseDTO;
import com.cmg.rms.rms_backend.util.LogUtil;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/rms")
public class RmsController {

  private final IRmsService rmsService;

  @GetMapping("/recipe_list")
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

  @GetMapping("/recipe_list/{recipeId}")
  public RecipeDetailsDTO getRecipeDetail(@PathVariable("recipeId") Long recipeId) {
    final String methodName = "getRecipeDetail";
    log.info(LogUtil.ENTRY, methodName);

    RecipeDetailsDTO response = rmsService.getRecipeDetails(recipeId);

    log.info(LogUtil.EXIT, methodName);
    return response;
  }
}
