package io.github.ruantarcisio.backend.admin.controller;

import io.github.ruantarcisio.backend.admin.service.AdminUserService;
import io.github.ruantarcisio.backend.users.data.UserResponse;
import io.github.ruantarcisio.backend.util.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUsersController {

  private final AdminUserService userService;

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<PagedResponse<UserResponse>> getUsers(
      @RequestParam(value = "page", defaultValue = "0") int page
  ) {
    PagedResponse<UserResponse> users = userService.getUsers(page);
    return ResponseEntity.ok(users);
  }

}
