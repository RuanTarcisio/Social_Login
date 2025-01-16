package io.github.ruantarcisio.backend.users.data;

import io.github.ruantarcisio.backend.users.Role;
import io.github.ruantarcisio.backend.users.User;
import lombok.Data;

@Data
public class UserResponse {
  private Long id;
  private Role role;
  private String firstName;
  private String lastName;
  private String email;
  private String profileImageUrl;

  public UserResponse(User user) {
    this.id = user.getId();
    this.role = user.getRole();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
    this.profileImageUrl = user.getProfileImageUrl();
  }
}
