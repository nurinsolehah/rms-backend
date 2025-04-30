package com.cmg.rms.rms_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rms_recipe_dtls")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RecipeDtls extends BaseEntity {
  @Id
  @Column(name = "recipe_dtls_id", unique = true, nullable = false)
  @GeneratedValue(generator = "recipe_dtls_id")
  Long recipeDtlsSeqno;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "recipe_id", nullable = false)
  public RecipeHdrs recipeHdrs;

  @Column(name = "recipe_id", updatable = false, insertable = false)
  Long recipeId;

  @Column(name = "recipe_ingredients")
  String recipeIngredients;

  @Column(name = "recipe_instructions")
  String recipeInstructions;

  @Column(name = "step_number")
  Long stepNumber;

  @Column(name = "quantity")
  BigDecimal quantity;

  @Column(name = "quantity_unit")
  String quantityUnit;
}
