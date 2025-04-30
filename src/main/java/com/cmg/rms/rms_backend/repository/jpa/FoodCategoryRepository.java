package com.cmg.rms.rms_backend.repository.jpa;

import com.cmg.rms.rms_backend.model.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
  FoodCategory findByCategoryName(String categoryName);

  FoodCategory findByCategoryCode(String categoryCode);
}
