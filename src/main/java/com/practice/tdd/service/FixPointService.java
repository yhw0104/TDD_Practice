package com.practice.tdd.service;

import org.springframework.stereotype.Service;

@Service
public class FixPointService implements PointService {

    private static final int POINT_RATE = 1000;
    @Override
    public int calculateAmount(Integer price) {

        return POINT_RATE;
    }
}
