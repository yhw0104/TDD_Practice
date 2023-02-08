package com.practice.tdd.entity;

import com.practice.tdd.Enum.MembershipType;
import com.practice.tdd.dto.MembershipDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id 자동증가 생성
    private Long membershipId;

    @Column
    @NotNull
    private MembershipType membershipType;

    @Column
    private int point;

    @CreationTimestamp  //INSERT 쿼리가 발생할 때, 현재 시간을 값으로 채워서 쿼리를 생성한다.
    @Column(nullable = false, length = 20, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp    //UPDATE 쿼리가 발생할 때, 현재 시간을 값으로 채워서 쿼리를 생성한다.
    @Column(length = 20)
    private LocalDateTime updatedAt;

    @Column
    @NotNull
    // member 참조 인덱스
    private Long memberIdIndex;

    public MembershipDto toDto(){
        MembershipDto membershipDto = MembershipDto.builder()
                .membershipType(membershipType)
                .point(point)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .memberIdIndex(memberIdIndex)
                .build();

        return membershipDto;
    }
}
