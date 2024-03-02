package com.example.querydsl.domain.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepositoryDsl {

    Page<Member> findByName(String name, Pageable pageable);

}
