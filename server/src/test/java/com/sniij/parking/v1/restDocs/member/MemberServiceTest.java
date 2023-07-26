package com.sniij.parking.v1.restDocs.member;

import com.sniij.parking.api.v1.member.dto.MemberPostDto;
import com.sniij.parking.api.v1.member.entity.Member;
import com.sniij.parking.api.v1.member.repository.MemberRepository;
import com.sniij.parking.api.v1.member.service.MemberService;
import com.sniij.parking.global.exception.BusinessLogicException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

//@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock   //
    private MemberRepository memberRepository;

    @InjectMocks    //
    private MemberService memberService;


    //@Test
    public void createMemberTest() {

        // given
        Member member = Member.builder()
                .memberId(1L)
                .email("abc@abc.com")
                .displayName("조만기")
                .password("1234")
                .build();

        MemberPostDto post = new MemberPostDto();
        post.setEmail("abc@abc.com");
        post.setDisplayName("조만기");
        post.setPassword("1234");


        given(memberRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(member));

        // when / then
        assertThrows(BusinessLogicException.class, () -> memberService.createMember(post));
    }
}
