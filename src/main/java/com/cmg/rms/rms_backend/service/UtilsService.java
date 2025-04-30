package com.cmg.rms.rms_backend.service;

import com.cmg.rms.rms_backend.IUtilsService;
import com.cmg.rms.rms_backend.dto.DropdownDTO;
import com.cmg.rms.rms_backend.model.FoodCategory;
import com.cmg.rms.rms_backend.repository.jooq.UtilsRepositoryJooq;
import com.cmg.rms.rms_backend.repository.jpa.FoodCategoryRepository;
import com.cmg.rms.rms_backend.util.LogUtil;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UtilsService implements IUtilsService {

  private final UtilsRepositoryJooq utilsRepositoryJooq;
  private final FoodCategoryRepository foodCategoryRepository;

  @Override
  public List<DropdownDTO> getFoodCategory() {
    final String methodName = "getFoodCategory";
    log.info(LogUtil.ENTRY_SERVICE, methodName);

    List<DropdownDTO> response = utilsRepositoryJooq.getFoodCategory();

    log.info(LogUtil.EXIT_SERVICE, methodName);
    return response;
  }

  @Override
  @Transactional
  public void removeFoodCategory(String categoryCode) {
    final String methodName = "removeFoodCategory";
    log.info(LogUtil.ENTRY_SERVICE, methodName);

    FoodCategory foodCategory = foodCategoryRepository.findByCategoryCode(categoryCode);
    foodCategory.setActiveFlag('I');
    foodCategory.setUpdatedBy(1L);
    foodCategory.setUpdatedDate(LocalDateTime.now());
    foodCategoryRepository.save(foodCategory);

    log.info(LogUtil.EXIT_SERVICE, methodName);
  }
}
