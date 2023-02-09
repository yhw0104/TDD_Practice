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

import javax.transaction.Transactional;
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
    void 멤버십서비스에서멤버십중복검사() {
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
        Long memberIdIndex = 1L;
        MembershipType membershipType = MembershipType.KAKAO;
        Integer point = 20000;

        Membership result = memberService.createMembership(memberIdIndex, membershipType, point);

        //then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("멤버십 조회 기능")
    void findMembership() {
        //given
        Member member = Member.builder()
                .userId("userA")
                .userPassword("userAPassword")
                .userName("userA")
                .build();

        Membership NaverUserA = Membership.builder()
                .memberIdIndex(1L)
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        Membership KakaoUserA = Membership.builder()
                .memberIdIndex(1L)
                .membershipType(MembershipType.KAKAO)
                .point(10000)
                .build();

        memberRepository.save(member);
        membershipRepository.save(NaverUserA);
        membershipRepository.save(KakaoUserA);

        //when
        List<Membership> findMembership = memberService.read(1L);

        //then
        assertThat(findMembership.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("멤버십 상세조회 실패 - 존재하지 않음")
    void NoMembership() {
        //given
        Member member = Member.builder()
                .userId("userA")
                .userPassword("userAPassword")
                .userName("userA")
                .build();

        Membership userA = Membership.builder()
                .memberIdIndex(1L)
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        memberRepository.save(member);
        membershipRepository.save(userA);

        //when
        Long memberIdIndex = 2L;
        MembershipType membershipType = MembershipType.NAVER;

        Membership result = memberService.readDetailMembership(memberIdIndex, membershipType);

        //then
        Assertions.assertThat(result).isNull();
    }

    @Test
    @DisplayName("멤버십 상세조회 성공")
    void findDetailMembership() {
        //given
        Member member = Member.builder()
                .userId("userA")
                .userPassword("userAPassword")
                .userName("userA")
                .build();

        Membership userA = Membership.builder()
                .memberIdIndex(1L)
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        memberRepository.save(member);
        membershipRepository.save(userA);
        //when
        Membership membership = memberService.readDetailMembership(1L, MembershipType.NAVER);

        //then
        assertThat(membership).isNotNull();
        assertThat(membership.getMembershipType()).isEqualTo(MembershipType.NAVER);
        assertThat(membership.getPoint()).isEqualTo(10000);
    }

    @Test
    @DisplayName("멤버십 삭제 실패 / 멤버십이 존재하지 않음")
    @Transactional
    void deleteMembership_NoMembership() {
        //given
        Member member = Member.builder()
                .userId("userA")
                .userPassword("userAPassword")
                .userName("userA")
                .build();

        memberRepository.save(member);

        //when
        Long membershipId = 1L;

        memberService.deleteMembership(membershipId);
        //then

    }

    @Test
    @DisplayName("멤버십 삭제 성공")
    @Transactional
    void deleteMemberhsip() {
        //given
        Member member = Member.builder()
                .userId("userA")
                .userPassword("userAPassword")
                .userName("userA")
                .build();

        Membership userA = Membership.builder()
                .memberIdIndex(1L)
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        memberRepository.save(member);
        membershipRepository.save(userA);

        //when
        memberService.deleteMembership(1L);

        //then
        assertThat(membershipRepository.findByMembershipId(1L)).isNull();

    }
}
