package com.example.swaggerui.controller.member;

import com.example.swaggerui.application.member.MemberService;
import lombok.RequiredArgsConstructor;
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
}
