package com.example.mybatis.application.member;

import com.example.mybatis.domain.member.Member;
import com.example.mybatis.domain.member.MemberMapper;
import com.example.mybatis.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    public String getMemberNamesJpa() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(Member::getName).collect(Collectors.joining(","));
    }

    public String getMemberNamesMybatis() {
        List<Member> members = memberMapper.findAll();
        return members.stream().map(Member::getName).collect(Collectors.joining(","));
    }

}
