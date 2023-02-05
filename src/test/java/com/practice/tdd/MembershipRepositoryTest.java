package com.practice.tdd;


import com.practice.tdd.Enum.MembershipErrorResult;
import com.practice.tdd.Enum.MembershipType;
import com.practice.tdd.entity.Membership;
import com.practice.tdd.repository.MembershipRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class MembershipRepositoryTest {

    @Autowired
    MembershipRepository membershipRepository;

//    @Test
//    void 레파지토리isNotNull(){
//
//        Assertions.assertThat(membershipRepository).isNotNull();
//    }


    @Test
    void 멤버십등록() {
        //given
        Membership member = Membership.builder()
                .userId("userA")
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        //when

        // 실제로는 dto로 받아와서 엔티티에 값을 넣어준다. 하지만 테스트 코드 작성이므로 바로 엔티티에 저장후 값을 확인한다.
        Membership result = membershipRepository.save(member);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUserId()).isEqualTo(member.getUserId());
        assertThat(result.getMembershipType()).isEqualTo(member.getMembershipType());
        assertThat(result.getPoint()).isEqualTo(member.getPoint());
    }

    @Test
    void 사용자가이미등록한멤버십일때_중복검사(){
        //given
        Membership member = Membership.builder()
                .userId("userA")
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        //when
        Membership result = membershipRepository.save(member);
        Membership findResult = membershipRepository.findByUserIdAndMembershipType("userA", MembershipType.NAVER);

        //then
        assertThat(findResult.getId()).isEqualTo(1);
        assertThat(findResult.getUserId()).isEqualTo(result.getUserId());
        assertThat(findResult.getMembershipType()).isEqualTo(result.getMembershipType());
    }

    @Test
    @DisplayName("멤버십 조회 / 멤버십이 없을 때")
    void NoMembership(){
        List<Membership> findMembership = membershipRepository.findByUserId("userId");

        MembershipErrorResult result = null;
        if(findMembership.size() == 0) {
            result = MembershipErrorResult.MEMBERSHIP_NOT_FOUND;
        }
        assertThat(findMembership.size()).isEqualTo(0);
        Assertions.assertThat(result.getMessage()).isEqualTo("Membership Not found");
    }

    @Test
    @DisplayName("멤버십 조회 / 멤버십이 있을 때(2개)")
    void TwoMembership(){
        //given
        Membership KakaoUserA = Membership.builder()
                .userId("userA")
                .membershipType(MembershipType.KAKAO)
                .point(10000)
                .build();

        Membership NaverUserA = Membership.builder()
                .userId("userA")
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        membershipRepository.save(KakaoUserA);
        membershipRepository.save(NaverUserA);

        //when
        List<Membership> findResult = membershipRepository.findByUserId("userA");

        //then
        assertThat(findResult.size()).isEqualTo(2);
    }

}

