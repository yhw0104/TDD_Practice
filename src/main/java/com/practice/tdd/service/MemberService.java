package com.practice.tdd.service;

import com.practice.tdd.Enum.MembershipErrorResult;
import com.practice.tdd.Enum.MembershipType;
import com.practice.tdd.entity.Member;
import com.practice.tdd.entity.Membership;
import com.practice.tdd.repository.MemberRepository;
import com.practice.tdd.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Member createMember(String userId, String userPassword, String userName){
        Member result = memberRepository.findByUserId(userId);

        if(result != null){
            System.out.println("이미 등록된 회원입니다.");
            return null;
        } else {
            Member member = Member.builder()
                    .userId("userAId")
                    .userPassword("userAPassword")
                    .userName("userA")
                    .build();

            Member createMember = memberRepository.save(member);

            return createMember;
        }
    }

    public Membership createMembership(Long memberIdIndex, MembershipType membershipType, Integer point    /*나중에 MembershipDto로 변경*/) {

        Membership result = membershipRepository.findByMemberIdIndexAndMembershipType(memberIdIndex, membershipType);

        if(result != null) {
            System.out.println("이미 등록된 회원멤버십입니다.");
            return null;
        } else {
            // MembershipDto로 받은 데이터 toEntity()메서드를 사용하여 엔티티로 변경
            Membership membership = Membership.builder()
                    .membershipId(memberIdIndex)
                    .membershipType(membershipType)
                    .point(point)
                    .build();

            Membership makeMembership2 = membershipRepository.save(membership);

            return makeMembership2;
        }
    }

    public List<Membership> read(Long memberIdIndex /*dto로 받는다.*/) {
        // dto -> entity로 변경후 멤버십 조회
        List<Membership> findMembership = membershipRepository.findByMemberIdIndex(memberIdIndex);

        return findMembership;
    }

    public Membership readDetailMembership(Long memberIdIndex, MembershipType membershipType) {
        Membership detailMembership = membershipRepository.findByMemberIdIndexAndMembershipType(memberIdIndex, membershipType);
        if(detailMembership == null){
            System.out.println("해당 멤버십이 없습니다.");
            return null;
        }
            return detailMembership;
    }

    public void deleteMembership(Long membershipId) {
        Membership deleteMembership = membershipRepository.findByMembershipId(membershipId);

        if(deleteMembership == null){
            System.out.println("해당 멤버십이 없습니다.");
        }
        if(deleteMembership != null){
            membershipRepository.deleteByMembershipId(membershipId);
        }

    }
}
