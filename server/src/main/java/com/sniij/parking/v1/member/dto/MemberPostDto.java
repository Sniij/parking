package com.sniij.parking.v1.member.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class MemberPostDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank(message = "이름을 적어주세요.")
    private String displayName;

    @NotBlank
    private String password;

}
