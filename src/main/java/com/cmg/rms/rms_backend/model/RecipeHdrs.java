package com.cmg.rms.rms_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rms_recipe_hdrs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RecipeHdrs extends BaseEntity {

  @Id
  @Column(name = "recipe_id", unique = true, nullable = false)
  Long recipeSeqno;

  @Column(name = "description", nullable = false)
  String description;

  @Column(name = "recipe_name", nullable = false)
  String recipeName;

  @Column(name = "category", nullable = false)
  String category;

  @Column(name = "image")
  Byte[] image;

  @ManyToOne
  @JoinColumn(name = "recipe_id")
  public RecipeDtls recipeDtls;

  @ManyToOne
  @JoinColumn(name = "category_id")
  public FoodCategory foodCategory;
}
