package io.github.ruantarcisio.website.users.controller;

import io.github.ruantarcisio.website.config.ApplicationProperties;
import io.github.ruantarcisio.website.users.data.CreateUserRequest;
import io.github.ruantarcisio.website.users.data.ForgotPasswordRequest;
import io.github.ruantarcisio.website.users.data.UpdateUserPasswordRequest;
import io.github.ruantarcisio.website.users.data.UserResponse;
import io.github.ruantarcisio.website.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

  private final UserService userService;
  private final ApplicationProperties applicationProperties;

  /**
   * Register a new user. The user will be created with the default role USER. Verification email will
   * be sent to the user.
   */
  @PostMapping
  public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
    UserResponse user = userService.create(request);
    return ResponseEntity.ok(user);
  }


  /**
   * Verify the email of the user, redirect to the login page.
   */
  @GetMapping("/verify-email")
  public RedirectView verifyEmail(@RequestParam String token) {
    userService.verifyEmail(token);
    return new RedirectView(applicationProperties.getLoginPageUrl());
  }

  /**
   * Request a password reset email
   */
  @PostMapping("/forgot-password")
  public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest req) {
    userService.forgotPassword(req.getEmail());
    return ResponseEntity.ok().build();
  }

  /**
   * Reset the password of an existing user, uses the password reset token
   * <p>
   * Only allowed with the password reset token
   */
  @PatchMapping("/reset-password")
  public ResponseEntity<Void> resetPassword(
          @Valid @RequestBody UpdateUserPasswordRequest requestDTO) {
    userService.resetPassword(requestDTO);
    return ResponseEntity.ok().build();
  }
}