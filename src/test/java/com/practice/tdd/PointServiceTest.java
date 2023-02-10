package com.practice.tdd;

import com.practice.tdd.Enum.MembershipType;
import com.practice.tdd.entity.Member;
import com.practice.tdd.entity.Membership;
import com.practice.tdd.repository.MemberRepository;
import com.practice.tdd.repository.MembershipRepository;
import com.practice.tdd.service.MemberService;
import com.practice.tdd.service.RatePointService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PointServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("포인트 적립 테스트")
    void savePoint() {
        //given
        Member userA = Member.builder()
                .userId("userAId")
                .userPassword("userAPassword")
                .userName("userA")
                .build();

        Membership userAMembership = Membership.builder()
                .membershipType(MembershipType.NAVER)
                .memberIdIndex(1L)
                .point(10000)
                .build();

        Member member = memberRepository.save(userA);
        Membership membership = membershipRepository.save(userAMembership);

        //when
        Long expectMemberIdIndex = 1L;
        MembershipType expectMembershipType = MembershipType.NAVER;
        int price = 10000;
        memberService.MembershipPoint(expectMemberIdIndex, expectMembershipType, price);

        Membership result = membershipRepository.findByMemberIdIndexAndMembershipType(expectMemberIdIndex, expectMembershipType);

        //then
        Assertions.assertThat(result.getPoint()).isEqualTo(10100);
    }
}
