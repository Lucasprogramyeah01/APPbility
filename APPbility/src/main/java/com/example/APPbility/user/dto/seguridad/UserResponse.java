package com.example.APPbility.user.dto.seguridad;

import com.example.APPbility.user.model.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.example.APPbility.user.model.User;

import java.util.Set;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        Set<UserRole> rol,
        String color,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String token,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String refreshToken
) {

    public static UserResponse of (User user) {

        return new UserResponse(user.getId(), user.getUsername(), user.getRoles(), user.getColor(), null, null);
    }

    public static UserResponse of (User user, String token, String refreshToken) {
        return new UserResponse(user.getId(), user.getUsername(), user.getRoles(), user.getColor(), token, refreshToken);
    }

}