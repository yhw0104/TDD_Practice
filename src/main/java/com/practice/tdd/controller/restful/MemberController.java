package com.practice.tdd.controller.restful;

import com.practice.tdd.dto.MemberDto;
import com.practice.tdd.entity.Member;
import com.practice.tdd.entity.Membership;
import com.practice.tdd.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @PostMapping("/create")
    public Member createMember(MemberDto memberDto) {
        Member member = memberDto.toEntity();

        String userId = member.getUserId();
        String userPassword = member.getUserPassword();
        String userName = member.getUserName();

        Member create = memberService.createMember(userId, userPassword, userName);
        return create;
    }

    @GetMapping("/{id}")
    public List<Membership> readMember(@PathVariable("id") Long id){
        return memberService.read(id);
    }
}

