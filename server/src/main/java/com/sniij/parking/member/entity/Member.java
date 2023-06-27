package com.sniij.parking.member.entity;

import javax.persistence.Entity;

@Entity
public class Member {

    Long memberId;

    String displayName;

    String password;
}
