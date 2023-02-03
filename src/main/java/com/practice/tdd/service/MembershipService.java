package com.practice.tdd.service;

import com.practice.tdd.Enum.MembershipType;
import com.practice.tdd.entity.Membership;
import com.practice.tdd.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipService {

    @Autowired
    MembershipRepository membershipRepository;


    public Membership AddMembership(String userId, MembershipType membershipType, Integer point) {
        Membership alreadyExist = membershipRepository.findByUserIdAndMembershipType(userId, membershipType);

        if(alreadyExist != null){
//            throw new RuntimeException("이미 존재하는 멤버쉽 회원 입니다.");
            System.out.println("이미 존재하는 멤버쉽회원 입니다.");
            return alreadyExist;
        } else {
            Membership makeMembership = Membership.builder()
                    .userId(userId)
                    .membershipType(membershipType)
                    .point(point)
                    .build();

            Membership expect = membershipRepository.save(makeMembership);
            return expect;
        }
    }
}
