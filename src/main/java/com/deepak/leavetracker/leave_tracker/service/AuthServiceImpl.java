package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.entity.Role;
import com.deepak.leavetracker.leave_tracker.entity.UserAccount;
import com.deepak.leavetracker.leave_tracker.entity.UserRole;
import com.deepak.leavetracker.leave_tracker.repository.RoleRepository;
import com.deepak.leavetracker.leave_tracker.repository.UserAccountRepository;
import com.deepak.leavetracker.leave_tracker.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserAccountRepository userAccountRepository,
                                  PasswordEncoder passwordEncoder,
                                  RoleRepository roleRepository,
                                  UserRoleRepository userRoleRepository){
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserAccount register(UserAccount userAccount){

        // encode password
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));

        // enable user
        userAccount.setEnabled(true);

        // save user
        UserAccount savedUser = userAccountRepository.save(userAccount);

        // fetch Default user role
        Role role = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found."));

        // map user to role
        UserRole userRole = new UserRole();
        userRole.setUserAccount(savedUser);
        userRole.setRole(role);

        userRoleRepository.save(userRole);

        return savedUser;
    }
}
