package com.example.excel.controller.member;

import com.example.excel.application.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/excel")
    public ResponseEntity<Resource> excel() {
        return memberService.excel();
    }

    @GetMapping("/temp-file-upload")
    public String tempFileUpload() {
        return memberService.tempFileUpload();
    }
}
