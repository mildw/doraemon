package com.example.async.domain.tenant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryDsl {

    Page<Member> findByName(String name, Pageable pageable);

}
