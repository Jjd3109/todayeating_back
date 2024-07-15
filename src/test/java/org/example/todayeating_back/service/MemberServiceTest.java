package org.example.todayeating_back.service;

import jakarta.transaction.Transactional;
import org.example.todayeating_back.config.JwtTokenProvider;
import org.example.todayeating_back.entity.MemberInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    @Transactional
    public void 회원가입_정상() {
        //given
        String email = "test@test.co.kr";
        String password = "1234";

        //when
        MemberInfo member = memberService.saveMember(email, password);

        // then
        assertThat(member).isNotNull();
        assertThat(member.getEmail()).isEqualTo(email);

    }

    @Test
    @Transactional
    public void 로그인_성공() {
        // given
        String email = "test_login@test.co.kr";
        String password = "password123";

        // 회원가입
        MemberInfo member = memberService.saveMember(email, password);

        // when
        String jwtToken = memberService.login(member.getEmail(), password);

        // then

        // Token에서 회원 정보 받아오기.
        assertThat(member.getEmail()).isEqualTo(jwtTokenProvider.getUsername(jwtToken));
    }


}