package com.cmg.rms.rms_backend.controller;

import com.cmg.rms.rms_backend.IUtilsService;
import com.cmg.rms.rms_backend.dto.DropdownDTO;
import com.cmg.rms.rms_backend.util.LogUtil;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/utils")
public class UtilsController {

  private final IUtilsService utilsService;

  /**
   * @return a list of food categories as {@link DropdownDTO}s. Each {@link DropdownDTO} contains
   *     the category code as the value and the category name as the description.
   */
  @GetMapping("/food_category")
  public List<DropdownDTO> getFoodCategory() {
    final String methodName = "getFoodCategory";
    log.info(LogUtil.ENTRY, methodName);

    List<DropdownDTO> response = utilsService.getFoodCategory();

    log.info(LogUtil.EXIT, methodName);
    return response;
  }

  /**
   * Removes a food category by its category code by setting its active flag to inactive.
   *
   * @param categoryCode the code of the category to be removed; if null, no operation is performed
   */
  @PostMapping("/food_category/remove")
  public void removeFoodCategory(@RequestParam(required = false) String categoryCode) {
    final String methodName = "removeFoodCategory";
    log.info(LogUtil.ENTRY, methodName);

    utilsService.removeFoodCategory(categoryCode);

    log.info(LogUtil.EXIT, methodName);
  }
}
