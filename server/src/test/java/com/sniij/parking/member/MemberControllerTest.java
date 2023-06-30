package com.sniij.parking.member;

import com.google.gson.Gson;
import com.sniij.parking.api.v1.member.dto.MemberPostDto;
import com.sniij.parking.api.v1.member.entity.Member;
import com.sniij.parking.api.v1.member.repository.MemberRepository;
import com.sniij.parking.api.v1.member.service.MemberService;
import com.sniij.parking.global.exception.BusinessLogicException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import javax.transaction.Transactional;


import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Transactional // test 가 끝나면 rollback 처리
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;


    @MockBean
    private MemberService memberService;


    @DisplayName("MemberController - Post Test")
    @Test
    void postMemberTest() throws Exception {
        // given
        MemberPostDto post = new MemberPostDto();
        post.setEmail("abc@abc.com");
        post.setDisplayName("조만기");
        post.setPassword("1234");

        Member member = Member.builder()
                .email(post.getEmail())
                .displayName(post.getDisplayName())
                .password(post.getPassword())
                .build();
        member.setMemberId(1L);

        given(memberService.createMember(Mockito.any(MemberPostDto.class)))
                .willReturn(member);

        String content = gson.toJson(post);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/v1/member/signup")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/v1/member/"))));
    }

    @Test
    void getMemberTest() throws Exception {
        // =================================== postMember()를 이용한 테스트 데이터 생성 시작
        // given
        MemberPostDto post = new MemberPostDto();
        post.setEmail("abc@abc.com");
        post.setDisplayName("조만기");
        post.setPassword("1234");

        String content = gson.toJson(post);

        ResultActions postActions =
                mockMvc.perform(
                        post("/v1/member/signup")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );
        // ===================================postMember()를 이용한 테스트 데이터 생성 끝

        String location = postActions.andReturn().getResponse().getHeader("Location");

        // when / then
        mockMvc.perform(
                        get(location).accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())    // (4)
                .andExpect(jsonPath("$.displayName").value(post.getDisplayName()));
    }



}
