package io.github.ruantarcisio.website.users.data;

import io.github.ruantarcisio.website.users.Role;
import io.github.ruantarcisio.website.users.User;
import lombok.Data;

@Data
public class UserResponse {
  private Long id;
  private Role role;
  private String firstName;
  private String lastName;
  private String email;

  public UserResponse(User user) {
    this.id = user.getId();
    this.role = user.getRole();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
  }
}
