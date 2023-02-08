package com.practice.tdd;


import com.practice.tdd.Enum.MembershipErrorResult;
import com.practice.tdd.Enum.MembershipType;
import com.practice.tdd.entity.Member;
import com.practice.tdd.entity.Membership;
import com.practice.tdd.repository.MemberRepository;
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
    private MembershipRepository membershipRepository;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    void 레파지토리isNotNull(){

        assertThat(memberRepository).isNotNull();
        assertThat(membershipRepository).isNotNull();
    }


    @Test
    void 멤버등록() {
        //given
        Member member = Member.builder()
                .userId("userAId")
                .userPassword("userAPassword")
                .userName("userA")
                .build();

        //when
        Member member1 = memberRepository.save(member);

        //then
        assertThat(member1.getId()).isNotNull();
        assertThat(member1.getUserId()).isEqualTo(member.getUserId());
        assertThat(member1.getUserPassword()).isEqualTo(member.getUserPassword());
        assertThat(member1.getUserName()).isEqualTo(member.getUserName());
    }

    @Test
    void 멤버등록_멤버아이디중복(){
        //given
        Member member = Member.builder()
                .userId("userAId")
                .userPassword("userAPassword")
                .userName("userA")
                .build();

        //when
        Member result = memberRepository.findByUserId("userAID");

        //then
        assertThat(result.getUserId()).isEqualTo(member.getUserId());
    }

    @Test
    void 멤버십등록() {
        //given
        Member member = Member.builder()
                .userId("userA")
                .userPassword("userAPassword")
                .userName("userA")
                .build();

        Membership member1 = Membership.builder()
                .memberIdIndex(1L)
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        //when

        // 실제로는 dto로 받아와서 엔티티에 값을 넣어준다. 하지만 테스트 코드 작성이므로 바로 엔티티에 저장후 값을 확인한다.
        Member memberResult = memberRepository.save(member);
        Membership membershipResult = membershipRepository.save(member1);

        //then
        assertThat(memberResult.getId()).isNotNull();
        assertThat(member.getUserId()).isEqualTo(memberResult.getUserId());
        assertThat(membershipResult.getMembershipType()).isEqualTo(member1.getMembershipType());
        assertThat(membershipResult.getPoint()).isEqualTo(member1.getPoint());
    }

    @Test
    void 사용자가이미등록한멤버십일때_중복검사(){
        //given
        Member member = Member.builder()
                .userId("userA")
                .userPassword("userAPassword")
                .userName("userA")
                .build();

        Membership member1 = Membership.builder()
                .memberIdIndex(1L)
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        //when
        Member memberResult = memberRepository.save(member);
        Membership result = membershipRepository.save(member1);
        Membership findResult = membershipRepository.findByMemberIdAndMembershipType(1L, MembershipType.NAVER);

        //then
        assertThat(findResult.getMemberIdIndex()).isEqualTo(1L);
        assertThat(findResult.getMembershipType()).isEqualTo(result.getMembershipType());
        assertThat(findResult.getPoint()).isEqualTo(10000);
    }

    @Test
    @DisplayName("멤버십 조회 / 멤버십이 없을 때")
    void NoMembership(){
        List<Membership> findMembership = membershipRepository.findByMemberIdIndex(1L);

        MembershipErrorResult result = null;
        if(findMembership.size() == 0) {
            result = MembershipErrorResult.MEMBERSHIP_NOT_FOUND;
        }
        assertThat(findMembership.size()).isEqualTo(0);
        assertThat(result.getMessage()).isEqualTo("Membership Not found");
    }

    @Test
    @DisplayName("멤버십 조회 / 멤버십이 있을 때(2개)")
    void TwoMembership(){
        //given
        Member member = Member.builder()
                .userId("userA")
                .userPassword("userAPassword")
                .userName("userA")
                .build();

        Membership KakaoUserA = Membership.builder()
                .memberIdIndex(1L)
                .membershipType(MembershipType.KAKAO)
                .point(10000)
                .build();

        Membership NaverUserA = Membership.builder()
                .memberIdIndex(1L)
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        memberRepository.save(member);
        membershipRepository.save(KakaoUserA);
        membershipRepository.save(NaverUserA);

        //when
        List<Membership> findResult = membershipRepository.findByMemberIdIndex(1L);

        //then
        assertThat(findResult.size()).isEqualTo(2);
    }

}

