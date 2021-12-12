package com.rgbk21.utils;

import org.springframework.lang.Nullable;

import java.util.Map;

public class CommonUtils {

  public static ErrorInfo createErrorInfo(String errorCode, String message) {
    ErrorInfo errorInfo = new ErrorInfo();
    errorInfo.setCode(errorCode);
    errorInfo.setMessageText(message);
    return errorInfo;
  }

  @Nullable
  public static String getEnvVariable(String key) {
    Map<String, String> env = System.getenv();

    for (String name : env.keySet()) {
      if (name.equals(key)) {
        return env.get(name);
      }
    }
    return null;
  }

}
