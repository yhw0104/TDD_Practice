package com.practice.tdd.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table
public class Membership {

    @Id
    private Long id;

    private String userId;

    private String membershipName;

    private int point;
}
