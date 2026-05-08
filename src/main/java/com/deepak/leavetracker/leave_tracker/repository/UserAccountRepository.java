package com.deepak.leavetracker.leave_tracker.repository;

import com.deepak.leavetracker.leave_tracker.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

    @Query("""
    SELECT DISTINCT u FROM UserAccount u
    JOIN FETCH u.userRoles ur
    JOIN FETCH ur.role
    WHERE u.username = :username
    """)
    Optional<UserAccount> findByUsername(String username);

    void deleteByUsername(String username);
}
