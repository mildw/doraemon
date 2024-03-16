package com.example.mybatis.domain.member;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public interface MemberMapper extends SqlSession {

    @Select("SELECT * FROM member")
    List<Member> findAll();
}