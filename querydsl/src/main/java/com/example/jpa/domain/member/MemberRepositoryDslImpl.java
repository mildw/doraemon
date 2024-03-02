package com.example.jpa.domain.member;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import static com.example.jpa.domain.member.QMember.member;

public class MemberRepositoryDslImpl extends QuerydslRepositorySupport implements MemberRepositoryDsl{
    public MemberRepositoryDslImpl() {
        super(Member.class);
    }


    @Override
    public Page<Member> findByName(String name, Pageable pageable) {
        JPQLQuery<Member> query = from(member)
                .where(
                        eqName(name)
                );

        QueryResults<Member> results =
                getQuerydsl().applyPagination(pageable, query).fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private static BooleanExpression eqName(String name) {
        if(name == null || name.isBlank()) {
            return null;
        }
        return member.name.contains(name);
    }
}
