package com.sniij.parking.v1.member.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter
@Setter
public class MemberPostDto {

    @NotBlank(message = "이메일을 적어주세요.")
    @Email
    private String email;

    @NotBlank(message = "이름을 적어주세요.")
    private String displayName;

    @NotBlank(message = "비밀번호를 적어주세요.")
    //@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

}
