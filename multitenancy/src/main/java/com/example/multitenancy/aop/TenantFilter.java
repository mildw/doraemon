package com.example.multitenancy.aop;

import com.example.core.multitenancy.TenantContext;
import com.example.core.multitenancy.TenantContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {

        // 조건에 따라 테넌트 변경
        TenantContextHolder.setTenantContext(new TenantContext(1L, "test"));

        filterChain.doFilter(request, response);

        TenantContextHolder.resetTenantContext();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<String> shouldNotFilterList = new ArrayList<>();
        shouldNotFilterList.add("/health");

        return shouldNotFilterList.contains(request.getRequestURI());
    }

    @Override
    public void destroy() {
        TenantContextHolder.resetTenantContext();
    }
}
