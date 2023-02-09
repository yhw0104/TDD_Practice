package com.practice.tdd.repository;

import com.practice.tdd.Enum.MembershipType;
import com.practice.tdd.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Membership findByMemberIdIndexAndMembershipType(Long memberId, MembershipType membershipType);

    List<Membership> findByMemberIdIndex(Long memberIdIndex);

    void deleteByMembershipId(Long membershipId);

    Membership findByMembershipId(Long membershipId);
}

