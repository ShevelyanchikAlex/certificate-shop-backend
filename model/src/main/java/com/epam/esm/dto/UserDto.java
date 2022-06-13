package com.epam.esm.dto;

import com.epam.esm.domain.user.Role;
import com.epam.esm.domain.user.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    private String name;
    private String email;
    private Role role;
    private Status status;
}
