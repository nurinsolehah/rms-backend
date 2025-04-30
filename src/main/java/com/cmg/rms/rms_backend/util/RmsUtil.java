package com.cmg.rms.rms_backend.util;

import org.springframework.lang.NonNull;

public class RmsUtil {
  public static @NonNull <T> T checkNotNull(T checkItem) {
    if (checkItem == null) {
      throw new NullPointerException("Null value found");
    }
    return checkItem;
  }
}
