package io.github.ruantarcisio.backend.util.exception;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiException extends RuntimeException {
  private String message;
  private int status = 400;
  private Map<String, String> errors;
}
