package io.github.ruantarcisio.backend.users.data;

import io.github.ruantarcisio.backend.util.validators.PasswordMatch;
import io.github.ruantarcisio.backend.util.validators.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@PasswordMatch(passwordField = "password", passwordConfirmationField = "passwordConfirmation")
public class CreateUserRequest {
  @Email
  @Unique(columnName = "email", tableName = "user", message = "User with this email already exists")
  private String email;
  @NotNull
  @Length(min = 8)
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "must contain at least one uppercase letter, one lowercase letter, and one digit.")
  private String password;
  private String passwordConfirmation;
  private String firstName;
  private String lastName;
}
