package com.example.entitylistener;

import com.example.entitylistener.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("dv")
@SpringBootTest(classes = EntityListenerApplication.class)
class MemberTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void testMemberLifecycle() {
        // Insert
        Member member = Member.create("John");
        entityManager.persist(member);
        assertNotNull(member.getId());
        entityManager.flush();

        // Select
        Member select = entityManager.find(Member.class, 1L);

        Member foundMember = entityManager.find(Member.class, member.getId());
        assertNotNull(foundMember);
        assertEquals("John", foundMember.getName());
        entityManager.flush();

        // Update
        foundMember.rename("Jane");
        entityManager.flush();

        // Delete
        entityManager.remove(foundMember);
        entityManager.flush();
    }
}