package io.github.ruantarcisio.website.users.repository;

import io.github.ruantarcisio.website.users.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

}
