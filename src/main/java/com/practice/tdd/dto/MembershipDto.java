package com.practice.tdd.dto;


import com.practice.tdd.Enum.MembershipType;
import com.practice.tdd.entity.Membership;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipDto {

    private Long id;
    private String userId;
    private MembershipType membershipType;
    private int point;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Membership toEntity(){
        Membership membership = Membership.builder()
                .id(id)
                .userId(userId)
                .membershipType(membershipType)
                .point(point)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        return membership;
    }
}
