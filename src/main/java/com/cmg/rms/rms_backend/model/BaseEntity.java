package com.cmg.rms.rms_backend.model;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

  @Column(name = "active_flag")
  private Character activeFlag;

  @Column(name = "created_by", nullable = false)
  private Long createdBy;

  @Column(name = "created_date", nullable = false, length = 29)
  private LocalDateTime createdDate;

  @Column(name = "updated_by", nullable = false)
  private Long updatedBy;

  @Column(name = "updated_date", nullable = false, length = 29)
  private LocalDateTime updatedDate;
}
