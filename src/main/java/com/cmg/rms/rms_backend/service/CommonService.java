package com.cmg.rms.rms_backend.service;

import com.cmg.rms.rms_backend.ICommonService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommonService implements ICommonService {
  //   @Value("${rms.development.mode:false}")
  //   private String developmentMode;

  //   @Override
  //   public SecUserDTO getAuthenticatedUser(Authentication authentication) {
  //     final String methodName = "getAuthenticatedUser";
  //     log.info(LogUtil.ENTRY_SERVICE, methodName);

  //     // String admin = "admin";

  //     SecUserDTO authenticatedUser = null;
  //     // if (developmentMode.equals("true")) {
  //       SecUser currentUser = secUserRepository.findByUserLoginName(level1Username);
  //       authenticatedUser = secUserRepositoryJooq.findByUserId(currentUser.getUserId());
  //     // } else {
  //     //   authenticatedUser =
  //     //       CommonUtil.isExist(authentication) ? (SecUserDTO) authentication.getPrincipal() :
  // null;
  //     // }

  //     log.info(LogUtil.EXIT_SERVICE, methodName);
  //     return authenticatedUser;
  //   }

  public static BufferedImage byteToImage(byte[] image) throws IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image);
    BufferedImage readImage = ImageIO.read(byteArrayInputStream);
    return readImage;
  }
}
