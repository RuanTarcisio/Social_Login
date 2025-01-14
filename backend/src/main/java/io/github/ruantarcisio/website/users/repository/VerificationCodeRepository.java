package io.github.ruantarcisio.website.users.repository;

import io.github.ruantarcisio.website.users.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    @Query("SELECT vc FROM VerificationCode vc WHERE vc.code = :code")
    Optional<VerificationCode> findByCode(String code);
}
