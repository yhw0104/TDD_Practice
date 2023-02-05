package com.practice.tdd;

import com.practice.tdd.Enum.MembershipType;
import com.practice.tdd.entity.Membership;
import com.practice.tdd.repository.MembershipRepository;
import com.practice.tdd.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Member;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private MemberService memberService;

    @Test
    void 멤버십서비스에서중복검사(){
        //given

        //제일 먼저 멤버십이 있다는 가정을 한다.
        Membership member = Membership.builder()
                .userId("userA")
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        Membership userA = membershipRepository.save(member);

        //when
        String userId = "userA";
        MembershipType membershipType = MembershipType.KAKAO;
        Integer point = 20000;

        Membership result = memberService.create(userId, membershipType, point);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userA.getUserId());
        assertThat(result.getMembershipType()).isEqualTo(MembershipType.KAKAO);
        assertThat(result.getPoint()).isEqualTo(20000);
    }

    @Test
    @DisplayName("멤버십 조회 기능")
    void findMembership(){
        //given
        Membership NaverUserA = Membership.builder()
                .userId("userA")
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        Membership KakaoUserA = Membership.builder()
                .userId("userA")
                .membershipType(MembershipType.KAKAO)
                .point(10000)
                .build();

        Membership naverUserA = membershipRepository.save(NaverUserA);
        Membership kakaoUserA = membershipRepository.save(KakaoUserA);

        //when
        List<Membership> findMembership = memberService.read("userA");

        //then
        assertThat(findMembership.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("멤버십 상세조회 실패 - 존재하지 않음")
    void NoMembership() {
        //given
        //when
        //then
    }

    @Test
    @DisplayName("멤버십 상세조회 실패 - 본인이 아님")
    void NotOwnMembership() {

    }

    @Test
    @DisplayName("멤버십 상세조회 성공")
    void findDetailMembership() {

    }
}
