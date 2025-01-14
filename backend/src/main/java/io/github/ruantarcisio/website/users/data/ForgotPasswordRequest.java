package io.github.ruantarcisio.website.users.data;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
  @Email
  private String email;
}
