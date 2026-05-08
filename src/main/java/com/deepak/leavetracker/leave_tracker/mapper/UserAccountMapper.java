package com.deepak.leavetracker.leave_tracker.mapper;

import com.deepak.leavetracker.leave_tracker.dto.request.UserAccountRequestDTO;
import com.deepak.leavetracker.leave_tracker.dto.response.UserAccountResponseDTO;
import com.deepak.leavetracker.leave_tracker.entity.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {

    UserAccount toEntity(UserAccountRequestDTO dto);

    @Mapping(target = "roles", expression = "java(mapRoles(userAccount))")
    UserAccountResponseDTO toDTO(UserAccount userAccount);

    List<UserAccountResponseDTO> toDTOList(List<UserAccount> users);

    // map roles
    default List<String> mapRoles(UserAccount user) {

        if (user.getUserRoles() == null) {
            return List.of();
        }

        return user.getUserRoles().stream()
                .map(ur -> ur.getRole().getRoleName())
                .toList();
    }
}
