package com.example.security.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
public class CustomerMember extends User {
    private Member member;
    public CustomerMember(Member member) {
        super(member.getUsername(), member.getPassword(), getAuthorities(member.getRoles()));
        this.member=member;
    }
    private static Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
          return roles.stream()
                  .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                  .collect(Collectors.toList()); // [ROLE_USER, ROLE_MANAGER, ROLE_ADMIN]
    }
}
