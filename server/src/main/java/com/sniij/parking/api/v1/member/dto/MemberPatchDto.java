package com.sniij.parking.api.v1.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;


@Getter
@Setter
public class MemberPatchDto {

    @Nullable
    private String displayName;

    @Nullable
    private String password;

}
