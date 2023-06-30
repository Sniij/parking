package com.sniij.parking.api.v1.member.controller;


import com.sniij.parking.api.v1.member.dto.MemberPatchDto;
import com.sniij.parking.api.v1.member.dto.MemberResponseDto;
import com.sniij.parking.api.v1.member.entity.Member;
import com.sniij.parking.api.v1.member.dto.MemberPostDto;
import com.sniij.parking.api.v1.member.service.MemberService;
import com.sniij.parking.global.utils.UriCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/v1/member")
public class MemberController {

private final static String MEMBER_DEFAULT_URL = "/v1/member";

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> postMember(@Valid @RequestBody MemberPostDto memberPostDto) {

        Member member = memberService.createMember(memberPostDto);

        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, member.getMemberId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable @Positive Long memberId) {

        Member member = memberService.findVerifiedMember(memberId);

        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .displayName(member.getDisplayName())
                .build();

        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }


    @PatchMapping("/{memberId}")
    public ResponseEntity<HttpStatus> patchMember(@PathVariable @Positive Long memberId,
                                                         @RequestBody MemberPatchDto memberPatchDto){

        memberService.updateMember(memberId, memberPatchDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<HttpStatus> deleteMember(@PathVariable @Positive Long memberId){


        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
