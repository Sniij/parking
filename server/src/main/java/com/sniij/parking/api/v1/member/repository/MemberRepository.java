package com.sniij.parking.api.v1.member.repository;

import com.sniij.parking.api.v1.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(member_id) > 0 THEN 1 ELSE 0 END result FROM member WHERE email = ?1", nativeQuery = true)
    int existsByEmail(String email);


    @Query(value = "SELECT m FROM Member m WHERE m.email = :email", nativeQuery = true)
    Optional<Member> findByEmail(String email);
}
