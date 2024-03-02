package com.example.querydsl.controller.member;

import com.example.querydsl.application.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/search")
    public String getMemberNames(
            @RequestParam String name,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        return memberService.getMemberNames(name, page, size);
    }

}
