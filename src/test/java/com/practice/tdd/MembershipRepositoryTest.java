package com.practice.tdd;


import com.practice.tdd.entity.Membership;
import com.practice.tdd.repository.MembershipRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@DataJpaTest
public class MembershipRepositoryTest {

    @Autowired
    MembershipRepository membershipRepository;

    @Test
    void 레파지토리isNotNull(){

        Assertions.assertThat(membershipRepository).isNotNull();
    }


    @Test
    void 멤버십등록() {
        //given
        Membership member = Membership.builder()
                .userId("userA")
                .membershipName("네이버")
                .point(10000)
                .build();

        //when

        //then
    }
}

