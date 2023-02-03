package com.practice.tdd.membershipRepository;

import com.practice.tdd.Enum.MembershipType;
import com.practice.tdd.entity.Membership;
import com.practice.tdd.repository.MembershipRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class MembershipRepositoryTest {

    @Autowired
    private MembershipRepository membershipRepository;

    //  Null인지 검사하는 테스트는 이제 불필요하므로 제거 --> 성공후 리팩토링
//    @Test
//    public void MembershipRepository가Null이아님() {
//        Assertions.assertThat(membershipRepository).isNotNull();
//    }

    @Test
    void 멤버쉽_등록(){
        //given
        final Membership membership = Membership.builder()
                .userId("userId")
                //.membershipName("네이버") --> 리팩토링
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        //when
        final Membership result = membershipRepository.save(membership);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUserId()).isEqualTo("userId");
//      assertThat(result.MembershipName).isEqualTo("네이버"); --> 리팩토링
        assertThat(result.getMembershipType()).isEqualTo(MembershipType.NAVER);
        assertThat(result.getPoint()).isEqualTo(10000);
    }

    @Test
    void 멤버쉽을_등록한_사용자_중복테스트(){
        //given
        final Membership membership = Membership.builder()
                .userId("userA")
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        //when
        membershipRepository.save(membership);
        final Membership result = membershipRepository.findByUserIdAndMembershipType("userA", MembershipType.NAVER);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUserId()).isEqualTo(membership.getUserId());
        assertThat(result.getMembershipType()).isEqualTo(MembershipType.NAVER);
        assertThat(result.getPoint()).isEqualTo(10000);


    }

}
