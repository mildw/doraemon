package com.example.async.application;

import com.example.async.domain.tenant.Member;
import com.example.async.domain.tenant.MemberRepository;
import com.example.core.multitenancy.TenantContext;
import com.example.core.multitenancy.TenantContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestService {

    private final MemberRepository memberRepository;

    private final LocalTestClient localTestClient;

    public String request() {
        String result = localTestClient.test();
        System.out.println("method : "+result);
        return result;
    }

    public String requestAndSaveData() {
        TenantContextHolder.setTenantContext(new TenantContext(
                1L,
                "test"
        ));
        Optional<Member> optMember = memberRepository.findById(1L);
        optMember.ifPresent(member -> System.out.println(member.getName()));
        String result = localTestClient.test();
        System.out.println("method : "+result);
        return result;
    }

    public Boolean runtimeException() {
        String result = localTestClient.runtimeException();
        System.out.println("method : "+result);
        return true;
    }
}
