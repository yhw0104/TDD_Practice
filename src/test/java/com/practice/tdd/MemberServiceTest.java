package com.practice.tdd;

import com.practice.tdd.Enum.MembershipType;
import com.practice.tdd.entity.Member;
import com.practice.tdd.entity.Membership;
import com.practice.tdd.repository.MemberRepository;
import com.practice.tdd.repository.MembershipRepository;
import com.practice.tdd.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    void 멤버십서비스에서중복검사(){
        //given

        //제일 먼저 멤버십이 있다는 가정을 한다.
        Member member = Member.builder()
                .userId("userA")
                .userPassword("userAPassword")
                .userName("userA")
                .build();

        Membership member1 = Membership.builder()
                .memberIdIndex(1L)
                .membershipType(MembershipType.KAKAO)
                .point(10000)
                .build();

        Member memberUserA = memberRepository.save(member);
        Membership userA = membershipRepository.save(member1);

        //when
        String userId = "userB";
        Long memberIdIndex = 1L;
        String userPassword = "userBPassword";
        String memberName = "userB";

        MembershipType membershipType = MembershipType.KAKAO;
        Integer point = 20000;

        Member memberResult = memberService.createMember(userId, userPassword, memberName);
        Membership result = memberService.createMembership(memberIdIndex, membershipType, point);

        //then
        assertThat(result.getMembershipId()).isNotNull();
        assertThat(memberResult.getId()).isNotNull();
        assertThat(result.getMembershipType()).isEqualTo(MembershipType.KAKAO);
        assertThat(result.getPoint()).isEqualTo(20000);
    }

    @Test
    @DisplayName("멤버십 조회 기능")
    void findMembership(){
        //given
        Member member = Member.builder()
                .userId("userA")
                .userPassword("userAPassword")
                .userName("userA")
                .build();

        Membership NaverUserA = Membership.builder()
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        Membership KakaoUserA = Membership.builder()
                .membershipType(MembershipType.KAKAO)
                .point(10000)
                .build();

        Membership naverUserA = membershipRepository.save(NaverUserA);
        Membership kakaoUserA = membershipRepository.save(KakaoUserA);

        //when
        List<Membership> findMembership = memberService.read(1L);

        //then
        assertThat(findMembership.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("멤버십 상세조회 실패 - 존재하지 않음")
    void NoMembership() {
        //given
        Long id = 1L;

        //when
        Optional<Membership> result = memberService.readDetailMembership(id);

        //then
        Assertions.assertThat(result).isNull();
    }

    @Test
    @DisplayName("멤버십 상세조회 실패 - 본인이 아님")
    void NotOwnMembership() {
        //given
        Membership userA = Membership.builder()
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        membershipRepository.save(userA);

        //when
        memberService.readDetailMembership(1L);
        //다시해보기 멤버십ID를 어터캐 할지 생각해보기( 내가봤을 때 member엔티티를 만들어서 참조인덱스로 만드는게 제일 베스트)

        //then
    }

    @Test
    @DisplayName("멤버십 상세조회 성공")
    void findDetailMembership() {

    }
}
