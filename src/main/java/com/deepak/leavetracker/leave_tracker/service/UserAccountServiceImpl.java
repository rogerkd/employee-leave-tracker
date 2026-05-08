package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.dto.response.ApiResponse;
import com.deepak.leavetracker.leave_tracker.entity.UserAccount;
import com.deepak.leavetracker.leave_tracker.repository.RoleRepository;
import com.deepak.leavetracker.leave_tracker.repository.UserAccountRepository;
import com.deepak.leavetracker.leave_tracker.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService{

    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository,
                                  PasswordEncoder passwordEncoder,
                                  RoleRepository roleRepository,
                                  UserRoleRepository userRoleRepository){
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserAccount fetchUserAccount(String username){
        return userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username (" + username + "), doesn't exist."));
    }

    @Override
    public void deleteUserAccount(String username){

        UserAccount userAccount = fetchUserAccount(username);
        if(userAccount==null){
            throw new RuntimeException("User with username (" + username + "), doesn't exist.");
        }

        userAccountRepository.deleteByUsername(username);

    }


}
