package com.sniij.parking.v1.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberResponseDto {

    private Long memberId;

    private String displayName;


}
