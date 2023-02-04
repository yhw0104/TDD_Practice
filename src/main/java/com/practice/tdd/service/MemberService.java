package com.practice.tdd.service;

import com.practice.tdd.Enum.MembershipType;
import com.practice.tdd.entity.Membership;
import com.practice.tdd.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MembershipRepository membershipRepository;

    public Membership create(String userId, MembershipType membershipType, Integer point    /*나중에 MembershipDto로 변경*/) {
        Membership result = membershipRepository.findByUserIdAndMembershipType(userId, membershipType);

        if(result != null) {
            System.out.println("이미 등록된 회원멤버십입니다.");

            return null;
        } else {
            // MembershipDto로 받은 데이터 toEntity()메서드를 사용하여 엔티티로 변경
            Membership member = Membership.builder()
                    .userId(userId)
                    .membershipType(membershipType)
                    .point(point)
                    .build();

            Membership makeMembership2 = membershipRepository.save(member);

            return makeMembership2;
        }
    }
}
