package io.github.ruantarcisio.website.users.service;

import io.github.ruantarcisio.website.users.User;
import io.github.ruantarcisio.website.users.VerificationCode;
import io.github.ruantarcisio.website.users.data.CreateUserRequest;
import io.github.ruantarcisio.website.users.data.UserResponse;
import io.github.ruantarcisio.website.users.jobs.SendWelcomeEmailJob;
import io.github.ruantarcisio.website.users.repository.UserRepository;
import io.github.ruantarcisio.website.users.repository.VerificationCodeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jobrunr.scheduling.BackgroundJobRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final VerificationCodeRepository verificationCodeRepository;

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
}