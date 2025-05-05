package com.cmg.rms.rms_backend.service;

import com.cmg.rms.rms_backend.IRmsUsersService;
import com.cmg.rms.rms_backend.dto.RmsRegistrationDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationRequestDTO;
import com.cmg.rms.rms_backend.exception.ExceptionCode;
import com.cmg.rms.rms_backend.exception.RmsException;
import com.cmg.rms.rms_backend.mapper.UserListMapper;
import com.cmg.rms.rms_backend.model.Users;
import com.cmg.rms.rms_backend.repository.jooq.RmsUsersRepositoryJooq;
import com.cmg.rms.rms_backend.repository.jpa.UsersRepository;
import com.cmg.rms.rms_backend.security.UsersDTO;
import com.cmg.rms.rms_backend.util.LogUtil;
import com.cmg.rms.rms_backend.util.PaginationUtil;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RmsUsersService implements IRmsUsersService {

  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;
  private final RmsUsersRepositoryJooq rmsUsersRepositoryJooq;

  @Override
  @Transactional
  public void register(RmsRegistrationDTO requestDTO) {
    final String methodName = "register";
    log.info(LogUtil.ENTRY_SERVICE, methodName);

    Users users = usersRepository.findByUsername(requestDTO.username()).orElse(null);
    if (users != null) {
      throw new RmsException(ExceptionCode.USERNAME_UNAVAILABLE, "Username already in use.");
    }

    if (requestDTO.userRole() == null
        || !requestDTO.userRole().equals("ADMIN") && !requestDTO.userRole().equals("USER")) {
      throw new RmsException(ExceptionCode.BAD_REQUEST, "Invalid user role.");
    }

    Users user = new Users();
    user.setUsername(requestDTO.username());
    user.setPassword(passwordEncoder.encode(requestDTO.password()));
    user.setUserRole(requestDTO.userRole());
    user.setCreatedDate(LocalDateTime.now());
    user.setUpdatedDate(LocalDateTime.now());
    user.setActiveFlag('A');
    usersRepository.save(user);
    log.info(LogUtil.EXIT_SERVICE, methodName);
  }

  @Override
  public UsersDTO getUserDetails() {
    final String methodName = "getUserDetails";
    log.info(LogUtil.ENTRY_SERVICE, methodName);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    Users user = usersRepository.findByUsername(authentication.getName()).orElse(null);
    if (user == null) {
      throw new RmsException(ExceptionCode.NOT_FOUND, "User not found.");
    }

    log.info(LogUtil.EXIT_SERVICE, methodName);

    return new UsersDTO(user.getUserId(), user.getUsername(), user.getUserRole());
  }

  public List<UsersDTO> getUserList(String username, String userRole, PaginationRequestDTO pgDTO) {
    final String methodName = "getUserList";
    log.info(LogUtil.ENTRY_SERVICE, methodName);

    PaginationRequestDTO paginationRequestDTO =
        PaginationUtil.pageSorting(pgDTO, new UserListMapper(), false);

    List<UsersDTO> response =
        rmsUsersRepositoryJooq.getUserList(username, userRole, paginationRequestDTO);

    log.info(LogUtil.EXIT_SERVICE, methodName);
    return response;
  }
}
