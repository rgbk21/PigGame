package com.rgbk21.utils;

public class CommonUtils {

  public static ErrorInfo createErrorInfo(String errorCode, String message) {
    ErrorInfo errorInfo = new ErrorInfo();
    errorInfo.setCode(errorCode);
    errorInfo.setMessageText(message);
    return errorInfo;
  }

}
