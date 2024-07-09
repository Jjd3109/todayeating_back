package org.example.todayeating_back.controller;

import lombok.RequiredArgsConstructor;
import org.example.todayeating_back.config.JwtTokenProvider;
import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    /*
     * 회원 가입 메서드
     */
    @PostMapping("/api/v1/save/member")
    public ResponseEntity<?> saveMember(@RequestParam("email") String email, @RequestParam("password") String password){
        MemberInfo memberInfo = memberService.saveMember(email, password);
        return ResponseEntity.ok().body(memberInfo);
    }

    @GetMapping("/api/v1/login/member")
    public ResponseEntity<?> findMember(@RequestParam("email") String email, @RequestParam("password") String password){
        String token = jwtTokenProvider.createToken(email, memberService.loadUserByUsername(email).getAuthorities());

        return ResponseEntity.ok().body(token);
    }
}
