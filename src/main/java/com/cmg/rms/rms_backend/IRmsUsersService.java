package com.cmg.rms.rms_backend;

import com.cmg.rms.rms_backend.dto.RmsRegistrationDTO;
import com.cmg.rms.rms_backend.dto.paging.PaginationRequestDTO;
import com.cmg.rms.rms_backend.security.UsersDTO;
import java.util.List;

public interface IRmsUsersService {
  void register(RmsRegistrationDTO requestDTO);

  UsersDTO getUserDetails();

  List<UsersDTO> getUserList(String username, String userRole, PaginationRequestDTO pgDTO);
}
