package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.dto.response.ApiResponse;
import com.deepak.leavetracker.leave_tracker.entity.UserAccount;

public interface UserAccountService {

    UserAccount fetchUserAccount(String username);

    void deleteUserAccount(String username);

}
