package com.cmg.rms.rms_backend.service;

import com.cmg.rms.rms_backend.IRmsService;
import com.cmg.rms.rms_backend.dto.RecipeDetailsDTO;
import com.cmg.rms.rms_backend.dto.RecipeListDTO;
import com.cmg.rms.rms_backend.dto.RecipeListRequestDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationRequestDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationResponseDTO;
import com.cmg.rms.rms_backend.mapper.RecipeListMapper;
import com.cmg.rms.rms_backend.repository.jooq.RmsRepositoryJooq;
import com.cmg.rms.rms_backend.util.LogUtil;
import com.cmg.rms.rms_backend.util.PaginationUtil;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RmsService implements IRmsService {

  private final RmsRepositoryJooq rmsRepositoryJooq;

  @Override
  public List<RecipeListDTO> getRecipeList(
      RecipeListRequestDTO requestDTO, PaginationRequestDTO pgDTO) {
    final String methodName = "getRecipeList";
    log.info(LogUtil.ENTRY_SERVICE, methodName);

    PaginationRequestDTO paginationRequestDTO =
        PaginationUtil.pageSorting(pgDTO, new RecipeListMapper(), false);

    List<RecipeListDTO> recipeList =
        rmsRepositoryJooq.getRecipeList(requestDTO, paginationRequestDTO);

    log.info(LogUtil.EXIT_SERVICE, methodName);
    return recipeList;
  }

  @Override
  public PaginationResponseDTO getRecipeListPages(
      RecipeListRequestDTO requestDTO, PaginationRequestDTO pgDTO) {
    final String methodName = "getRecipeListPages";
    log.info(LogUtil.ENTRY_SERVICE, methodName);
    Long total = rmsRepositoryJooq.getRecipeListPages(requestDTO);

    PaginationResponseDTO paginationResponseDTO = PaginationUtil.pagination(pgDTO.size(), total);

    log.info(LogUtil.EXIT_SERVICE, methodName);
    return paginationResponseDTO;
  }

  @Override
  public RecipeDetailsDTO getRecipeDetails(Long recipeId) {
    final String methodName = "getRecipeDetails";
    log.info(LogUtil.ENTRY_SERVICE, methodName);

    RecipeDetailsDTO response = rmsRepositoryJooq.getRecipeDetails(recipeId);

    log.info(LogUtil.EXIT_SERVICE, methodName);
    return response;
  }
}
