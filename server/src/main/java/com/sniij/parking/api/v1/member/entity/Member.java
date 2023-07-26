package com.sniij.parking.api.v1.member.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity(name = "MEMBER")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String displayName;

    @Column(nullable = false)
    private String password;

    @Column
    private LocalDateTime createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();



    @Builder
    public Member(Long memberId, String email, String displayName, String password) {
        this.memberId = memberId;
        this.email = email;
        this.displayName = displayName;
        this.password = password;
    }
}
