package com.deepak.leavetracker.leave_tracker.controller;

import com.deepak.leavetracker.leave_tracker.dto.request.UserAccountRequestDTO;
import com.deepak.leavetracker.leave_tracker.dto.response.ApiResponse;
import com.deepak.leavetracker.leave_tracker.dto.response.UserAccountResponseDTO;
import com.deepak.leavetracker.leave_tracker.entity.UserAccount;
import com.deepak.leavetracker.leave_tracker.mapper.UserAccountMapper;
import com.deepak.leavetracker.leave_tracker.security.user.CustomUserDetails;
import com.deepak.leavetracker.leave_tracker.service.AuthService;
import com.deepak.leavetracker.leave_tracker.service.UserAccountService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserAccountMapper userAccountMapper;

    public AuthController(AuthService authService,
                                 UserAccountMapper userAccountMapper){
        this.authService = authService;
        this.userAccountMapper = userAccountMapper;
    }

    // register new user
    @PostMapping("/register")
    public ApiResponse<UserAccountResponseDTO> register(@RequestBody UserAccountRequestDTO dto){
        UserAccount userAccount = authService.register(userAccountMapper.toEntity(dto));

        return new ApiResponse<UserAccountResponseDTO>("User registered successfully", userAccountMapper.toDTO(userAccount));
    }

    // login user

}
