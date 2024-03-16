package com.example.mybatis.controller.member;

import com.example.mybatis.application.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/jpa/members/name")
    public String getMemberNamesJpa() {
        return memberService.getMemberNamesJpa();
    }

    @GetMapping("/mybatis/members/name")
    public String getMemberNamesMybatis() {
        return memberService.getMemberNamesMybatis();
    }

}
