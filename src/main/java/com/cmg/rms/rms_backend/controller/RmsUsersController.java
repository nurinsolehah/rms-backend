package com.cmg.rms.rms_backend.controller;

import com.cmg.rms.rms_backend.IRmsUsersService;
import com.cmg.rms.rms_backend.dto.LoginRequestDTO;
import com.cmg.rms.rms_backend.dto.RmsRegistrationDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationRequestDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationResponseDTO;
import com.cmg.rms.rms_backend.security.JwtTokenUtil;
import com.cmg.rms.rms_backend.security.UsersDTO;
import com.cmg.rms.rms_backend.service.RmsUserDetailsService;
import com.cmg.rms.rms_backend.util.LogUtil;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/users")
public class RmsUsersController {

  private final IRmsUsersService rmsUsersService;
  private final RmsUserDetailsService rmsUserDetailsService;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtToken;

  @PostMapping("/register")
  public void register(@RequestBody RmsRegistrationDTO requestDTO) {
    final String methodName = "register";
    log.info(LogUtil.ENTRY, methodName);

    rmsUsersService.register(requestDTO);

    log.info(LogUtil.EXIT, methodName);
  }

  @PostMapping("/login")
  public String loginGenerateToken(@RequestBody LoginRequestDTO requestDTO) {
    final String methodName = "login";
    log.info(LogUtil.ENTRY, methodName);

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(requestDTO.username(), requestDTO.password()));

    UserDetails userDetails = rmsUserDetailsService.loadUserByUsername(requestDTO.username());

    return jwtToken.generateToken(userDetails);
  }

  /**
   * Retrieves a list of users based on the given parameters. The list is sorted by the given sort
   * column and direction, and is paginated by the given page and size. The user list is filtered
   * based on the given username and user role.
   *
   * @param username the username to filter by. If null, all usernames are returned.
   * @param userRole the user role to filter by. If null, all user roles are returned.
   * @param sort the column to sort by. If null, the list is not sorted.
   * @param sortDirection the direction to sort by. If null, the list is sorted in ascending order.
   * @param page the page of the list to retrieve. If null, the first page is retrieved.
   * @param size the size of the list to retrieve. If null, the default size is used.
   * @return a list of users that match the given parameters.
   */
  @GetMapping("/user_list")
  public List<UsersDTO> getUserList(
      @RequestParam(required = false) String username,
      @RequestParam(required = false) String userRole,
      @RequestParam(required = false) String sort,
      @RequestParam(required = false) String sortDirection,
      @RequestParam(required = false) Long page,
      @RequestParam(required = false) Long size) {
    final String methodName = "getUserList";
    log.info(LogUtil.ENTRY, methodName);

    PaginationRequestDTO paginationRequestDTO =
        new PaginationRequestDTO(sort, sortDirection, page, size);

    List<UsersDTO> response = rmsUsersService.getUserList(username, userRole, paginationRequestDTO);
    log.info(LogUtil.EXIT, methodName);
    return response;
  }

  /**
   * Retrieves a pagination object containing the total number of pages and the current page.
   *
   * @param username the username to filter by. If null, all usernames are returned.
   * @param userRole the user role to filter by. If null, all user roles are returned.
   * @param size the size of the list to retrieve. If null, the default size is used.
   * @return a pagination object containing the total number of pages and the current page.
   */
  @GetMapping("/user_list/page")
  public PaginationResponseDTO getUserListPages(
      @RequestParam(required = false) String username,
      @RequestParam(required = false) String userRole,
      @RequestParam(required = false) Long size) {
    final String methodName = "getUserListPages";
    log.info(LogUtil.ENTRY, methodName);

    PaginationRequestDTO pgDTO = new PaginationRequestDTO(null, null, null, size);
    PaginationResponseDTO response = rmsUsersService.getUserListPages(username, userRole, pgDTO);

    log.info(LogUtil.EXIT, methodName);
    return response;
  }
}
