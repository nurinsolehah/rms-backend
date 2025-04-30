package com.cmg.rms.rms_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rms_food_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FoodCategory extends BaseEntity {

  @Id
  @Column(name = "category_id", unique = true, nullable = false)
  @GeneratedValue(generator = "category_id")
  Long recipeDtlSeqno;

  @Column(name = "category_code", nullable = false)
  String categoryCode;

  @Column(name = "category_name", nullable = false)
  String categoryName;
}
