package com.cmg.rms.rms_backend.security;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersService {

  private final UsersRepositoryJooq userRepositoryJooq;

  public UsersDTO getByUserId(Long userId) {
    UsersDTO u = userRepositoryJooq.findByUserId(userId);
    return u;
  }
}
