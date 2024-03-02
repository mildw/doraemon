package com.example.jpa.application.member;

import com.example.jpa.domain.member.Member;
import com.example.jpa.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    public String getMemberNames(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Member> members = memberRepository.findByName(name, pageable);
        return members.stream().map(Member::getName).collect(Collectors.joining(","));
    }

}
