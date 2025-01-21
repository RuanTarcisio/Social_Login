package io.github.ruantarcisio.backend.admin.service;

import io.github.ruantarcisio.backend.users.User;
import io.github.ruantarcisio.backend.users.data.UserResponse;
import io.github.ruantarcisio.backend.users.repository.UserRepository;
import io.github.ruantarcisio.backend.util.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;

    public PagedResponse<UserResponse> getUsers(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<User> users = userRepository.findAll(pageable);
        return new PagedResponse<>(users.map(UserResponse::new));
    }
}
