package com.sniij.parking.v1.restDocs.member;

import com.google.gson.Gson;
import com.sniij.parking.api.v1.member.controller.MemberController;
import com.sniij.parking.api.v1.member.dto.MemberPostDto;
import com.sniij.parking.api.v1.member.entity.Member;
import com.sniij.parking.api.v1.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;


import java.util.List;
import java.util.Objects;

import static com.sniij.parking.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.sniij.parking.global.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;


//@Transactional // test 가 끝나면 rollback 처리
@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
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

        String content = gson.toJson(post);


        Member member = Member.builder()
                .email(post.getEmail())
                .displayName(post.getDisplayName())
                .password(post.getPassword())
                .build();
        member.setMemberId(1L);

        given(memberService.createMember(Mockito.any(MemberPostDto.class)))
                .willReturn(member);

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
                .andExpect(header().string("Location", is(startsWith("/v1/member/"))))
                .andDo(document(
                        "post-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("displayName").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                )
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 리소스의 URI")
                        )
                ));;
    }

    //@Test
    void getMemberTest() throws Exception {
        // =================================== postMember()를 이용한 테스트 데이터 생성 시작
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

        ResultActions postActions =
                mockMvc.perform(
                        post("/v1/member/signup")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );
        // ===================================postMember()를 이용한 테스트 데이터 생성 끝

        String location = postActions.andReturn().getResponse().getHeader("Location");
        System.out.println("location = "+location + " response = " + postActions.andReturn().getResponse());

        // when / then
        ResultActions getActions =
                mockMvc.perform(
                                    get("/v1")
                                            .accept(MediaType.APPLICATION_JSON)
                                            .contentType(MediaType.APPLICATION_JSON)
                                )
                                .andExpect(status().isOk())    // (4)
                                .andExpect(jsonPath("$.displayName").value(post.getDisplayName()));

    }

}
