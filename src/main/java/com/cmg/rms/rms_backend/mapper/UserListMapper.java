package com.cmg.rms.rms_backend.mapper;

public class UserListMapper extends ColumnMapper {
  public UserListMapper() {
    // COLUMN_MAP.put("userId", "userId");
    COLUMN_MAP.put("username", "username");
    COLUMN_MAP.put("userRole", "userRole");
  }
}
