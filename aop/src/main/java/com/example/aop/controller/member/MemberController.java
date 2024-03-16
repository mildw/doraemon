package com.example.aop.controller.member;

import com.example.aop.application.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/name")
    public String getMemberNames() {
        return memberService.getMemberNames();
    }

    @PreAuthorize("hasAuthority('ROLE1')")
    @GetMapping("/auth/role/1")
    public String role1() {
        return "role1";
    }

    @PreAuthorize("hasAuthority('ROLE2')")
    @GetMapping("/auth/role/2")
    public String role2() {
        return "role2";
    }

    @PreAuthorize("hasAnyAuthority('ROLE1','ROLE2')")
    @GetMapping("/auth/role/1,2")
    public String role1AndRole2() {
        return "role1 & role2";
    }
}
