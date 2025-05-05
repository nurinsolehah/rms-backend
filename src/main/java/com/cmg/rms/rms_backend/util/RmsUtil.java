package com.cmg.rms.rms_backend.util;

import org.springframework.lang.NonNull;

public class RmsUtil {
  public static @NonNull <T> T checkNotNull(T checkItem) {
    if (checkItem == null) {
      throw new NullPointerException("Null value found");
    }
    return checkItem;
  }

  public static <T> Boolean isExist(T value) {
    if (value == null) {
      return false;
    }

    if (value instanceof String) {
      if (((String) value).isBlank()) {
        return false;
      }
    }

    return true;
  }
}
