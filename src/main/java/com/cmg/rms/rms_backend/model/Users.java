package com.cmg.rms.rms_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rms_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Users extends BaseEntity {
  @Id
  @Column(name = "user_id", unique = true, nullable = false)
  Long userId;

  @Column(name = "user_role", nullable = false)
  String userRole;

  @Column(name = "username", nullable = false)
  String username;

  @Column(name = "password", nullable = false)
  String password;
}
