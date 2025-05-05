package com.cmg.rms.rms_backend.service;

import com.cmg.rms.rms_backend.IRmsService;
import com.cmg.rms.rms_backend.dto.CreateRecipeRequestDTO;
import com.cmg.rms.rms_backend.dto.FoodCategoryListDTO;
import com.cmg.rms.rms_backend.dto.RecipeDetailsDTO;
import com.cmg.rms.rms_backend.dto.RecipeListDTO;
import com.cmg.rms.rms_backend.dto.RecipeListRequestDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationRequestDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationResponseDTO;
import com.cmg.rms.rms_backend.exception.ExceptionCode;
import com.cmg.rms.rms_backend.exception.RmsException;
import com.cmg.rms.rms_backend.mapper.RecipeListMapper;
import com.cmg.rms.rms_backend.model.FoodCategory;
import com.cmg.rms.rms_backend.model.RecipeHdrs;
import com.cmg.rms.rms_backend.repository.jooq.RmsRepositoryJooq;
import com.cmg.rms.rms_backend.repository.jpa.FoodCategoryRepository;
import com.cmg.rms.rms_backend.repository.jpa.RecipeHdrsRepository;
import com.cmg.rms.rms_backend.security.UsersDTO;
import com.cmg.rms.rms_backend.util.LogUtil;
import com.cmg.rms.rms_backend.util.PaginationUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class RmsService implements IRmsService {

  private final RmsRepositoryJooq rmsRepositoryJooq;
  private final RecipeHdrsRepository recipeHdrsRepository;
  private final FoodCategoryRepository foodCategoryRepository;

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

    RecipeDetailsDTO recipe = rmsRepositoryJooq.getRecipeDetails(recipeId);

    log.info(LogUtil.EXIT_SERVICE, methodName);
    return recipe;
  }

  @Override
  public void getRecipeImage(Long recipeId, HttpServletResponse responseImage) throws IOException {
    final String methodName = "getRecipeDetailsImage";
    log.info(LogUtil.ENTRY_SERVICE, methodName);

    RecipeHdrs recipeImage = recipeHdrsRepository.getReferenceById(recipeId);
    byte[] imageData = recipeImage.getImage();

    if (imageData == null || imageData.length == 0) {
      throw new RmsException(ExceptionCode.NOT_FOUND, "Image not found.");
    }

    String contentType =
        switch ((imageData[0] & 0xFF) << 8 | (imageData[1] & 0xFF)) {
          case 0xFFD8 -> "image/jpeg";
          case 0x8950 -> "image/png";
          case 0x4749 -> "image/gif";
          default -> "application/octet-stream";
        };
    responseImage.setContentType(contentType);

    try (InputStream inputStream = new ByteArrayInputStream(imageData);
        OutputStream outputStream = responseImage.getOutputStream()) {
      IOUtils.copy(inputStream, outputStream);
      outputStream.flush();
      log.info(LogUtil.EXIT_SERVICE, methodName);
    }
  }

  @Override
  public void addRecipe(CreateRecipeRequestDTO requestDTO, UsersDTO user) {
    final String methodName = "addRecipe";
    log.info(LogUtil.ENTRY_SERVICE, methodName);

    // List<RecipeDtls> recipeDtlsList = new ArrayList<>();
    // List<RecipeInstructionsDTO> instructions = requestDTO.recipeInstructions();
    // for (int i = 0; i < instructions.size(); i++) {
    //   RecipeInstructionsDTO instruction = instructions.get(i);
    //   RecipeDtls recipeDtls = new RecipeDtls();
    //   recipeDtls.setRecipeHdrs(recipeHdrs);
    //   recipeDtls.setStepNumber((long) i + 1);
    //   recipeDtls.setRecipeIngredients(instruction.recipeIngredients());
    //   recipeDtls.setRecipeInstructions(instruction.recipeInstructions());
    //   recipeDtls.setQuantity(instruction.quantity());
    //   recipeDtls.setQuantityUnit(instruction.quantityUnit());
    //   recipeDtls.setCreatedBy(1L);
    //   recipeDtls.setCreatedDate(LocalDateTime.now());
    //   recipeDtls.setUpdatedBy(1L);
    //   recipeDtls.setUpdatedDate(LocalDateTime.now());
    //   recipeDtls.setActiveFlag('A');
    //   recipeDtlsList.add(recipeDtls);
    // }

    // recipeHdrsRepository.save(recipeHdrs);
    // recipeDtlsRepository.saveAll(recipeDtlsList);
    rmsRepositoryJooq.addRecipe(requestDTO, user);

    log.info(LogUtil.EXIT_SERVICE, methodName);
  }

  @Override
  @Transactional
  public void updateRecipeImage(Long recipeId, MultipartFile image, UsersDTO user) {
    final String methodName = "updateRecipeImage";
    log.info(LogUtil.ENTRY_SERVICE, methodName);

    RecipeHdrs recipeHdrs = recipeHdrsRepository.getReferenceById(recipeId);

    try {
      if (recipeHdrs != null) {
        recipeHdrs.setImage(image.getBytes());
        recipeHdrs.setUpdatedBy(user.userId());
        recipeHdrs.setUpdatedDate(LocalDateTime.now());
      }
    } catch (IOException e) {
      throw new RuntimeException("Image file not supported");
    }

    recipeHdrsRepository.save(recipeHdrs);

    log.info(LogUtil.EXIT_SERVICE, methodName);
  }

  @Override
  @Transactional
  public void updateRecipeDetails(Long recipeId, CreateRecipeRequestDTO requestDTO, UsersDTO user) {
    final String methodName = "updateRecipeDetails";
    log.info(LogUtil.ENTRY_SERVICE, methodName);

    RecipeHdrs recipeHdrs = recipeHdrsRepository.getReferenceById(recipeId);

    if (recipeHdrs.getUpdatedDate().isAfter(LocalDateTime.now())) {
      throw new RmsException(
          ExceptionCode.FORBIDDEN,
          "Recipe is updated by other user, please try again after some time");
    }

    recipeHdrs.setRecipeName(requestDTO.recipeName());
    recipeHdrs.setDescription(requestDTO.description());
    recipeHdrs.setUpdatedBy(user.userId());
    recipeHdrs.setUpdatedDate(LocalDateTime.now());
    recipeHdrs.setActiveFlag('A');
    recipeHdrs.setInstructions(requestDTO.instructions());
    recipeHdrs.setIngredients(requestDTO.ingredients());

    recipeHdrsRepository.save(recipeHdrs);
    // recipeDtlsRepository.save(recipeDtls);

    log.info(LogUtil.EXIT_SERVICE, methodName);
  }

  @Override
  public void deleteRecipe(Long recipeId) {
    final String methodName = "deleteRecipe";
    log.info(LogUtil.ENTRY_SERVICE, methodName);

    rmsRepositoryJooq.deleteRecipe(recipeId);

    log.info(LogUtil.EXIT_SERVICE, methodName);
  }

  @Override
  public List<FoodCategoryListDTO> getFoodCategory() {
    final String methodName = "getFoodCategory";
    log.info(LogUtil.ENTRY_SERVICE, methodName);

    List<FoodCategoryListDTO> response = rmsRepositoryJooq.getFoodCategory();

    log.info(LogUtil.EXIT_SERVICE, methodName);
    return response;
  }

  @Override
  @Transactional
  public void removeFoodCategory(Long categoryId, UsersDTO user) {
    final String methodName = "removeFoodCategory";
    log.info(LogUtil.ENTRY_SERVICE, methodName);

    FoodCategory foodCategory = foodCategoryRepository.getReferenceById(categoryId);
    foodCategory.setActiveFlag('I');
    foodCategory.setUpdatedBy(user.userId());
    foodCategory.setUpdatedDate(LocalDateTime.now());
    foodCategoryRepository.save(foodCategory);

    log.info(LogUtil.EXIT_SERVICE, methodName);
  }
}
