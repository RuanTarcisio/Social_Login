package io.github.ruantarcisio.website.users.repository;

import io.github.ruantarcisio.website.users.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

}
