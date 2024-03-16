package com.example.entitylistener.domain.member;

import com.example.entitylistener.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @PrePersist
    public void prePersist() {
        System.out.println("------------------------- insert 이전");
    }

    @PostPersist
    public void postPersist() {
        System.out.println("------------------------- insert 이후");
    }

    @PreUpdate
    public void preUpdate() {
        System.out.println("------------------------- Update 이전");
    }

    @PostUpdate
    public void postUpdate() {
        System.out.println("------------------------- Update 이후");
    }

    @PreRemove
    public void preRemove() {
        System.out.println("------------------------- Delete 이전");
    }

    @PostRemove
    public void postRemove() {
        System.out.println("------------------------- Delete 이후");
    }

    @PostLoad
    public void postLoad() {
        System.out.println("------------------------- Select 호출 이후");
    }

    private Member(String name) {
        this.name = name;
    }

    public static Member create(String name) {
        return new Member(name);
    }

    public void rename(String name) {
        this.name = name;
    }
}
