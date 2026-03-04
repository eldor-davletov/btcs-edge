package com.example.btcsedge.dto;


import com.example.btcsedge.domain.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Set<String> roles;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(
                        user.getRoles().stream()
                                .map(r -> r.getName().name())
                                .collect(Collectors.toSet())
                )
                .build();
    }
}

