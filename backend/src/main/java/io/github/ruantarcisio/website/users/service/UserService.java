package io.github.ruantarcisio.website.users.service;

import io.github.ruantarcisio.website.auth.SecurityUtil;
import io.github.ruantarcisio.website.users.PasswordResetToken;
import io.github.ruantarcisio.website.users.User;
import io.github.ruantarcisio.website.users.VerificationCode;
import io.github.ruantarcisio.website.users.data.CreateUserRequest;
import io.github.ruantarcisio.website.users.data.UpdateUserPasswordRequest;
import io.github.ruantarcisio.website.users.data.UpdateUserRequest;
import io.github.ruantarcisio.website.users.data.UserResponse;
import io.github.ruantarcisio.website.users.jobs.SendResetPasswordEmailJob;
import io.github.ruantarcisio.website.users.jobs.SendWelcomeEmailJob;
import io.github.ruantarcisio.website.users.repository.PasswordResetTokenRepository;
import io.github.ruantarcisio.website.users.repository.UserRepository;
import io.github.ruantarcisio.website.users.repository.VerificationCodeRepository;
import io.github.ruantarcisio.website.util.exception.ApiException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jobrunr.scheduling.BackgroundJobRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final VerificationCodeRepository verificationCodeRepository;
  private final PasswordResetTokenRepository passwordResetTokenRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public UserResponse create(@Valid CreateUserRequest request) {
    User user = new User(request);
    user = userRepository.save(user);
    sendVerificationEmail(user);
    return new UserResponse(user);
  }

  private void sendVerificationEmail(User user) {
    VerificationCode verificationCode = new VerificationCode(user);
    user.setVerificationCode(verificationCode);
    verificationCodeRepository.save(verificationCode);
    SendWelcomeEmailJob sendWelcomEmailJob = new SendWelcomeEmailJob(user.getId());
    BackgroundJobRequest.enqueue(sendWelcomEmailJob);
  }

  @Transactional
  public void verifyEmail(String code) {
    VerificationCode verificationCode = verificationCodeRepository.findByCode(code)
            .orElseThrow(() -> ApiException.builder().status(400).message("Invalid token").build());
    User user = verificationCode.getUser();
    user.setVerified(true);
    userRepository.save(user);
    verificationCodeRepository.delete(verificationCode);
  }

  @Transactional
  public void forgotPassword(String email) {
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> ApiException.builder().status(404).message("User not found").build());
    PasswordResetToken passwordResetToken = new PasswordResetToken(user);
    passwordResetTokenRepository.save(passwordResetToken);
    SendResetPasswordEmailJob sendResetPasswordEmailJob = new SendResetPasswordEmailJob(passwordResetToken.getId());
    BackgroundJobRequest.enqueue(sendResetPasswordEmailJob);
  }

  @Transactional
  public void resetPassword(UpdateUserPasswordRequest request) {
    PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(request.getPasswordResetToken())
            .orElseThrow(() -> ApiException.builder().status(404).message("Password reset token not found").build());
    if (passwordResetToken.isExpired()) {
      throw ApiException.builder().status(400).message("Password reset token is expired").build();
    }
    User user = passwordResetToken.getUser();
    user.updatePassword(request.getPassword());
    userRepository.save(user);
  }

  @Transactional
  public UserResponse update(UpdateUserRequest request) {
    User user = SecurityUtil.getAuthenticatedUser();
    user = userRepository.getReferenceById(user.getId());
    user.update(request);
    user = userRepository.save(user);
    return new UserResponse(user);
  }
  @Transactional
  public UserResponse updatePassword(UpdateUserPasswordRequest request) {
    User user = SecurityUtil.getAuthenticatedUser();
    if (user.getPassword() != null && !passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
      throw ApiException.builder().status(400).message("Wrong password").build();
    }
    user.updatePassword(request.getPassword());
    user = userRepository.save(user);
    return new UserResponse(user);
  }
}