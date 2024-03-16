package com.example.scheduler.task.member;

import com.example.scheduler.application.member.MemberService;
import com.example.scheduler.task.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
//@Profile(value = "!local")
@RequiredArgsConstructor
public class MemberTask {

    private final MemberService memberService;

    @Scheduled(fixedDelay = Schedule.FIXED_DELAY_1S)
    public void getName() {
        memberService.getMemberNames();
    }
}