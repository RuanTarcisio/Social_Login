package io.github.ruantarcisio.website.users.service;

import io.github.ruantarcisio.website.users.User;
import io.github.ruantarcisio.website.users.data.CreateUserRequest;
import io.github.ruantarcisio.website.users.data.UserResponse;
import io.github.ruantarcisio.website.users.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public UserResponse create(@Valid CreateUserRequest request) {
    User user = new User(request);
    user = userRepository.save(user);

    // TODO: Send verification email

    return new UserResponse(user);
  }

}
