package com.devsu.client.config;

import static com.devsu.client.commom.util.ResponseUtil.response;
import com.devsu.client.dto.GenericResponse;
import jakarta.validation.constraints.NotNull;
import static java.util.Objects.requireNonNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerConfig {

  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<GenericResponse> validationException(@NotNull WebExchangeBindException e) {
    return response("VALIDATION ERROR", getMessage(e.getAllErrors()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<GenericResponse> validationException(MethodArgumentNotValidException ex) {
    return response("VALIDATION ERROR", getMessage(ex.getAllErrors()), HttpStatus.BAD_REQUEST);
  }

  private String getMessage(List<ObjectError> errors) {
    return errors.stream()
      .map(DefaultMessageSourceResolvable::getDefaultMessage)
      .collect(Collectors.joining(", "));
  }

  @ExceptionHandler(HttpClientErrorException.class)
  public ResponseEntity<GenericResponse> validationException(@NotNull HttpClientErrorException e) {
    return response(e.getStatusText().toUpperCase(), e.getResponseBodyAsString(), HttpStatus.valueOf(e.getStatusCode().value()));
  }

  @ExceptionHandler(HttpStatusCodeException.class)
  public ResponseEntity<GenericResponse> httpClientException(@NotNull HttpStatusCodeException e) {
    return response("HTTP CLIENT ERROR", e.getResponseBodyAsString(), HttpStatus.valueOf(e.getStatusCode().value()));
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<GenericResponse> responseStatusException(@NotNull ResponseStatusException e) {
    return response(requireNonNull(HttpStatus.resolve(e.getStatusCode().value())).getReasonPhrase().toUpperCase(),
      e.getReason(),
      HttpStatus.valueOf(e.getStatusCode().value()));
  }
}
