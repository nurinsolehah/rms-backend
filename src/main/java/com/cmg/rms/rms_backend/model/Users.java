package com.cmg.rms.rms_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "rms_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements UserDetails {
  @Id
  @Column(name = "user_id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long userId;

  @Column(name = "user_role", nullable = false)
  String userRole;

  @Column(name = "username", nullable = false)
  String username;

  @Column(name = "password", nullable = false)
  String password;

  @Column(name = "created_date", nullable = false)
  LocalDateTime createdDate;

  @Column(name = "updated_date", nullable = false)
  LocalDateTime updatedDate;

  @Column(name = "active_flag", nullable = false)
  Character activeFlag;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(userRole));
  }
}
