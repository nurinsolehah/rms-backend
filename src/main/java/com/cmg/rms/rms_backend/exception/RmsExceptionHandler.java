package com.cmg.rms.rms_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class RmsExceptionHandler {
  public static ResponseEntity<ProblemDetail> handleRmsException(RmsException e) {
    return switch (e.code()) {
      case NOT_FOUND -> ResponseEntity.status(404).body(e.response(HttpStatus.NOT_FOUND));
      case BAD_REQUEST -> ResponseEntity.badRequest().body(e.response(HttpStatus.BAD_REQUEST));
      case FORBIDDEN -> ResponseEntity.status(401).body(e.response(HttpStatus.FORBIDDEN));
      case USERNAME_UNAVAILABLE ->
          ResponseEntity.badRequest().body(e.response(HttpStatus.CONFLICT));
      default -> ResponseEntity.status(400).body(e.response(HttpStatus.BAD_REQUEST));
    };
  }
}
