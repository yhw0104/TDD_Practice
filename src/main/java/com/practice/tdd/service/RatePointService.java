package com.practice.tdd.service;

import com.practice.tdd.Enum.MembershipType;
import com.practice.tdd.entity.Membership;
import com.practice.tdd.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatePointService implements PointService {

    private static final int POINT_RATE = 1;

    @Override
    public int calculateAmount(Integer price) {
        int point = price * POINT_RATE / 100 ;
        return point;
    }
}
