package com.sniij.parking.member;

import com.sniij.parking.api.v1.member.entity.Member;
import com.sniij.parking.api.v1.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


/*
*  DB test 에서 가장 중요한 것은 DB의 상태를 테스트 케이스 실행 이전으로 되돌려서 깨끗하게 만드는 것이다.
*  @DataJpaTest 는 @Transactional 을 포함하기 때문에 Rollback 이 된다.
* */


@DataJpaTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void saveMemberTest() {
        // given
        Member member = Member.builder()
                        .displayName("example")
                        .email("user@example.com")
                        .password("1234")
                        .build();

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertNotNull(savedMember);
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getDisplayName(), savedMember.getDisplayName());
        assertEquals(member.getPassword(), savedMember.getPassword());
    }

    @Test
    public void findByEmailTest() {
        // given
        Member member = Member.builder()
                .displayName("example")
                .email("user@example.com")
                .password("1234")
                .build();

        // when
        memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());

        // then
        assertTrue(findMember.isPresent());
        assertEquals(findMember.get().getEmail(), member.getEmail());
    }
}