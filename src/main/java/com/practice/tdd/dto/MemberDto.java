package com.practice.tdd.dto;


import com.practice.tdd.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private Long id;

    private String userId;

    private String userPassword;

    private String userName;

    public Member toEntity(){
        Member member = Member.builder()
                .id(id)
                .userId(userId)
                .userPassword(userPassword)
                .userName(userName)
                .build();

        return member;
    }
}
