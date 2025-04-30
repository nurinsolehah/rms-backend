package com.cmg.rms.rms_backend.exception;

import com.cmg.rms.rms_backend.util.RmsUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class RmsException extends RuntimeException {
  private ExceptionCode code;
  private String title;
  private String message;
  private StringBuffer messageDetails;

  public RmsException(ExceptionCode code, String message) {
    this.code = code;
    this.message = message;
  }

  public RmsException(ExceptionCode code, String title, String message) {
    this.code = code;
    this.title = title;
    this.message = message;
  }

  public RmsException(ExceptionCode code, String message, StringBuffer messageDetails) {
    this.code = code;
    this.message = message;
    this.messageDetails = messageDetails;
  }

  public ExceptionCode code() {
    return code;
  }

  public String title() {
    return title;
  }

  public String message() {
    return message;
  }

  public StringBuffer messageDetails() {
    return messageDetails;
  }

  public ProblemDetail response(HttpStatus status) {
    ProblemDetail problemDetail =
        ProblemDetail.forStatus(HttpStatus.valueOf(RmsUtil.checkNotNull(status.name())));

    problemDetail.setTitle(title);
    problemDetail.setDetail(message);
    problemDetail.setProperty("error", "PhIS Exception Error");
    problemDetail.setProperty("code", code.name());
    problemDetail.setProperty("messageDetails", messageDetails);
    // problemDetail.setType();
    return problemDetail;
  }
}
