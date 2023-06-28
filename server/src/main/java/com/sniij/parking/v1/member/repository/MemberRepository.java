package com.sniij.parking.v1.member.repository;

import com.sniij.parking.v1.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    //@Query(value = "SELECT m FROM Member m WHERE m.email = :email")
    @Query(value = "SELECT CASE WHEN COUNT(member_id) > 0 THEN 1 ELSE 0 END result FROM member WHERE email = ?1", nativeQuery = true)
    int existsByEmail(String email);


}
