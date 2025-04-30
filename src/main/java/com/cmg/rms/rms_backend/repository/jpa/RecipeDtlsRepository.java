package com.cmg.rms.rms_backend.repository.jpa;

import com.cmg.rms.rms_backend.model.RecipeDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeDtlsRepository extends JpaRepository<RecipeDtls, Long> {
  RecipeDtls findByRecipeId(Long recipeId);
}
