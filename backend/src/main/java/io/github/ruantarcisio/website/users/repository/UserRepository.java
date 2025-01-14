package io.github.ruantarcisio.website.users.repository;

import io.github.ruantarcisio.website.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
