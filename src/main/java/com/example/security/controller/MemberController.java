package com.example.security.controller;

import com.example.security.entity.Member;
import com.example.security.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/ui/list")
    public String main(){
        return "list"; // list.html
    }

    @GetMapping("/register")
    public String register(){
         return "register"; // register.html
    }

    @PostMapping("/register")
    public String register(Member member){
        // 패스워드 암호화
        memberService.register(member); // 등록
        return "redirect:/ui/list"; // Book 목록여주기(main)
    }
}
