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

    private Long membershipId;

    private MembershipType membershipType;
    private int point;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long memberIdIndex;


    public Membership toEntity(){
        Membership membership = Membership.builder()
                .membershipId(membershipId)
                .membershipType(membershipType)
                .point(point)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .memberIdIndex(memberIdIndex)
                .build();

        return membership;
    }
}
