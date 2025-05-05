package com.cmg.rms.rms_backend.controller;

import com.cmg.rms.rms_backend.exception.RmsException;
import com.cmg.rms.rms_backend.exception.RmsExceptionHandler;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(RmsException.class)
  public ResponseEntity<ProblemDetail> handleRmsException(RmsException e) {
    return RmsExceptionHandler.handleRmsException(e);
  }
}
