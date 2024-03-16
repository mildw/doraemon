package com.example.scheduler.application.member;

import com.example.scheduler.domain.member.Member;
import com.example.scheduler.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void getMemberNames() {
        List<Member> members = memberRepository.findAll();
        String names = members.stream().map(Member::getName).collect(Collectors.joining(","));
        log.info(names);
    }
}
