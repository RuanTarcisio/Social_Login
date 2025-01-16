package io.github.ruantarcisio.backend.users.jobs.handlers;

import java.util.List;

import io.github.ruantarcisio.backend.config.ApplicationProperties;
import io.github.ruantarcisio.backend.email.EmailService;
import io.github.ruantarcisio.backend.users.PasswordResetToken;
import io.github.ruantarcisio.backend.users.User;
import io.github.ruantarcisio.backend.users.jobs.SendResetPasswordEmailJob;
import io.github.ruantarcisio.backend.users.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
@RequiredArgsConstructor
public class SendResetPasswordEmailJobHandler implements JobRequestHandler<SendResetPasswordEmailJob> {

  private final PasswordResetTokenRepository passwordResetTokenRepository;
  private final EmailService emailService;
  private final ApplicationProperties applicationProperties;
  private final SpringTemplateEngine templateEngine;

  @Override
  @Transactional
  public void run(SendResetPasswordEmailJob sendResetPasswordEmailJob) throws Exception {
    PasswordResetToken resetToken = passwordResetTokenRepository.findById(sendResetPasswordEmailJob.getTokenId())
        .orElseThrow(() -> new IllegalArgumentException("Token not found"));
    if (!resetToken.isEmailSent()) {
      sendResetPasswordEmail(resetToken.getUser(), resetToken);
    }
  }

  private void sendResetPasswordEmail(User user, PasswordResetToken token) {
    String link = applicationProperties.getBaseUrl() + "/auth/reset-password?token=" + token.getToken();
    Context thymeleafContext = new Context();
    thymeleafContext.setVariable("user", user);
    thymeleafContext.setVariable("link", link);
    String htmlBody = templateEngine.process("password-reset", thymeleafContext);
    emailService.sendHtmlMessage(List.of(user.getEmail()), "Password reset requested", htmlBody);
    token.onEmailSent();
    passwordResetTokenRepository.save(token);
  }
}
