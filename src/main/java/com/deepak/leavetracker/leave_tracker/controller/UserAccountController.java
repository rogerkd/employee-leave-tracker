package com.deepak.leavetracker.leave_tracker.controller;

import com.deepak.leavetracker.leave_tracker.dto.response.ApiResponse;
import com.deepak.leavetracker.leave_tracker.dto.response.UserAccountResponseDTO;
import com.deepak.leavetracker.leave_tracker.entity.UserAccount;
import com.deepak.leavetracker.leave_tracker.mapper.UserAccountMapper;
import com.deepak.leavetracker.leave_tracker.service.UserAccountService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final UserAccountMapper userAccountMapper;

    public UserAccountController(UserAccountService userAccountService,
                                 UserAccountMapper userAccountMapper){
        this.userAccountService = userAccountService;
        this.userAccountMapper = userAccountMapper;
    }

    @GetMapping("/me")
    public ApiResponse<UserAccountResponseDTO> getCurrentUser(Authentication auth) {
        String username = auth.getName();

        UserAccount user = userAccountService.fetchUserAccount(username);

        return new ApiResponse<UserAccountResponseDTO>("User account fetched successfully", userAccountMapper.toDTO(user));
    }

    @DeleteMapping("/me")
    public ApiResponse<UserAccountResponseDTO> removeCurrentUser(Authentication auth) {
        String username = auth.getName();

        userAccountService.deleteUserAccount(username);

        return new ApiResponse<UserAccountResponseDTO>("User account deleted successfully", null);
    }


}
