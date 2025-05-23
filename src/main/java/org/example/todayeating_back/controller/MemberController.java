package org.example.todayeating_back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.config.JwtTokenProvider;
import org.example.todayeating_back.dto.request.MemberInfoRequest;
import org.example.todayeating_back.dto.response.Token;
import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    /*
     * 회원 가입 메서드
     */
    @PostMapping("/api/v1/save/member")
    public ResponseEntity<?> saveMember(@RequestBody MemberInfoRequest memberInfoRequest){
        try{
            MemberInfo memberInfo = memberService.saveMember(memberInfoRequest);
            return ResponseEntity.ok().body(memberInfo);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("회원 가입이 실패하였습니다.");
        }
    }

    /*
     * 회원 수정 메서드
     */
    @PostMapping("/api/v1/update/memberInfo")
    public ResponseEntity<?> updateMemberInfo(@RequestBody MemberInfoRequest memberInfoRequest){
        try{
            return ResponseEntity.ok().body(memberService.updateMember(memberInfoRequest));
        }catch(Exception e){
            return ResponseEntity.badRequest().body("회원 수정이 실패하였습니다.");
        }
    }

    /*
     * 로그인시 Token 발급 일단은 accessToken 만 사용.
     */
    @GetMapping("/api/v1/login/member")
    public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            String token = memberService.login(email, password); // accessToken 발급
            String refreshToken = memberService.createRefreshToken(email); // refreshToken 발급

          return ResponseEntity.ok().body(Token.token(token, refreshToken));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호가 틀립니다.");
        }
    }

    /*
     * 동일한 이름이 있나 확인
     */
    @GetMapping("/api/v1/find/email")
    public ResponseEntity<?> findEmail(@RequestParam("email") String email) {
        try {
            return ResponseEntity.ok().body(memberService.findEmail(email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 가입 실패");
        }
    }

    /*
     * 회원의 정보를 가져와서 처리
     */
    @GetMapping("/api/v1/find/memberInfo")
    public ResponseEntity<?> findMmeberInfo(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return ResponseEntity.ok().body(memberService.findEmailAll(authentication.getName()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보 불러오기 실패");
        }
    }

    /*
     * 회원의 정보를 가져와서 처리
     */
    @GetMapping("/api/v1/delete/token")
    public ResponseEntity<?> deleteToken(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            return ResponseEntity.ok().body(memberService.findEmailAll(authentication.getName()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보 불러오기 실패");
        }
    }
}
