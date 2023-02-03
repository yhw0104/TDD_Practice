package com.practice.tdd.membershipService;

import com.practice.tdd.Enum.MembershipErrorResult;
import com.practice.tdd.Enum.MembershipType;
import com.practice.tdd.entity.Membership;
import com.practice.tdd.repository.MembershipRepository;
import com.practice.tdd.service.MembershipService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MembershipServiceTest {

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private MembershipService membershipService;

    @Test
    void 멤버쉽등록실패_이미존재함(){
        //given
        final Membership membership = Membership.builder()
                .userId("userA")
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        //when
        final Membership userA = membershipRepository.save(membership);

        Membership result = membershipService.AddMembership("userA", MembershipType.NAVER, 10000);

        //then
        assertThat(userA.getId()).isNotNull();
        assertThat(userA.getUserId()).isEqualTo(result.getUserId());
        assertThat(userA.getMembershipType()).isEqualTo(result.getMembershipType());
    }

    @Test
    void 멤버쉽등록성공(){
        //given
        final Membership membership = Membership.builder()
                .userId("userA")
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        //when
        final Membership userA = membershipRepository.save(membership);

        Membership result = membershipService.AddMembership("userB", MembershipType.NAVER, 20000);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getMembershipType()).isEqualTo(MembershipType.NAVER);
        assertThat(result.getPoint()).isEqualTo(20000);
    }
}