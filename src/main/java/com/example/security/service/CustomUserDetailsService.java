package com.example.security.service;

import com.example.security.entity.CustomerMember;
import com.example.security.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member=memberService.findByUsername(username);
        // return member; // CustomerMember
        System.out.println("CustomUserDetailsService:" + member.getUsername());
        return new CustomerMember(member); // Authentication->View
    }
}
