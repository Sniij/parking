package com.sniij.parking.api.v1.member.service;


import com.sniij.parking.api.v1.member.dto.MemberPatchDto;
import com.sniij.parking.api.v1.member.dto.MemberPostDto;
import com.sniij.parking.api.v1.member.entity.Member;
import com.sniij.parking.api.v1.member.repository.MemberRepository;
import com.sniij.parking.global.exception.BusinessLogicException;
import com.sniij.parking.global.exception.ExceptionCode;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    //private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    public Member createMember(MemberPostDto memberPostDto){

        verifyExistsEmail(memberPostDto.getEmail());

        //String encryptedPassword = passwordEncoder.encode(memberPostDto.getPassword());

        Member member = Member.builder()
                                .email(memberPostDto.getEmail())
                                .displayName(memberPostDto.getDisplayName())
                                .password(memberPostDto.getPassword())
                                .build();

        return memberRepository.save(member);
    }


    public void updateMember(Long memberId, MemberPatchDto memberPatchDto) {

        Member foundMember = findVerifiedMember(memberId);

        Optional.ofNullable(memberPatchDto.getDisplayName())
                .ifPresent(name -> foundMember.setDisplayName(name));
        Optional.ofNullable(memberPatchDto.getPassword())
                .ifPresent(password -> foundMember.setPassword(password));


        memberRepository.save(foundMember);
    }

    public void deleteMember(Long memberId) {

        verifyExistsId(memberId);
        memberRepository.deleteById(memberId);
    }

    protected void verifyExistsEmail(String email) {

        // repository 에 email 이 존재하면 exception
        if(memberRepository.existsByEmail(email)==1){
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }

    }

    protected void verifyExistsId(Long id){

        // repository 에 member id 가 존재하지 않으면 exception
        if(!memberRepository.existsById(id)){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

    }

    public Member findVerifiedMember(Long id){

        Optional<Member> member = memberRepository.findById(id);

        return member.orElseThrow( ()->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND) );
    }


}
