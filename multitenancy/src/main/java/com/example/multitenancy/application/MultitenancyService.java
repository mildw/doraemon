package com.example.multitenancy.application;

import com.example.multitenancy.controller.rqrs.MultitenancyRs;
import com.example.multitenancy.domain.common.Template;
import com.example.multitenancy.domain.common.TemplateRepository;
import com.example.multitenancy.domain.tenant.Member;
import com.example.multitenancy.domain.tenant.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MultitenancyService {

    private final TemplateRepository templateRepository;
    private final MemberRepository memberRepository;

    public String getMemberNames() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(Member::getName).collect(Collectors.joining(","));
    }

    public MultitenancyRs test() {
        List<Template> templates = templateRepository.findAll();
        List<Member> members = memberRepository.findAll();
        String t = templates.stream().map(Template::getName).collect(Collectors.joining(","));
        String m = members.stream().map(Member::getName).collect(Collectors.joining(","));
        return new MultitenancyRs(t, m);
    }
}
