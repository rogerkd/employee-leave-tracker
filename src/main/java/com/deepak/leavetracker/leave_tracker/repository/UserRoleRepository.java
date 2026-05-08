package com.deepak.leavetracker.leave_tracker.repository;

import com.deepak.leavetracker.leave_tracker.entity.Role;
import com.deepak.leavetracker.leave_tracker.entity.UserAccount;
import com.deepak.leavetracker.leave_tracker.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    void deleteByUserAccount(UserAccount userAccount);

    void deleteByUserAccountAndRole(UserAccount userAccount, Role role);
}
