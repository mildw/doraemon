package com.example.inittableliquibase.application.member;

import com.example.inittableliquibase.domain.member.Member;
import com.example.inittableliquibase.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public String getMemberNames() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(Member::getName).collect(Collectors.joining(","));
    }
}
