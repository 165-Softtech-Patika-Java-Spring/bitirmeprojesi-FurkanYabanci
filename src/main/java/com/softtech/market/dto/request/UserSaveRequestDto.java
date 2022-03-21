package com.softtech.market.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserSaveRequestDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
