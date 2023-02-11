package com.practice.tdd.entity;

import com.practice.tdd.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String userId;

    @Column
    @NotNull
    private String userPassword;

    @Column
    @NotNull
    private String userName;

    public MemberDto toDto(){
        MemberDto memberDto = MemberDto.builder()
                .id(id)
                .userId(userId)
                .userPassword(userPassword)
                .userName(userName)
                .build();

        return memberDto;
    }
}
