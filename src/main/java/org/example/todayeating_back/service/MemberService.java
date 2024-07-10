package org.example.todayeating_back.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.config.JwtTokenProvider;
import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.repository.MemberInfoRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberInfoRepository memberInfoRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberInfo memberInfo = memberInfoRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("이메일 또는 비밀번호가 틀립니다."));

        return new User(
                memberInfo.getEmail(),
                memberInfo.getPassword(),
                memberInfo.getAuthorities()
        );
    }

    public MemberInfo saveMember(String email, String password) {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER"); // 일시적으로 USER 권한 넣어 놓기.
        MemberInfo memberInfo = MemberInfo.saveMember(email, passwordEncoder.encode(password), roles);
        return memberInfoRepository.save(memberInfo);
    }

    public String login(String email, String password) {
        UserDetails userDetails = loadUserByUsername(email);

        if (validatePassword(password, userDetails.getPassword())) {
            return jwtTokenProvider.createToken(email, userDetails.getAuthorities());
        } else {
            throw new UsernameNotFoundException("이메일 또는 비밀번호가 틀립니다.");
        }
    }


    private boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    //jwtToken 발급 및 redis 에 넣어주기. && 지금은 DB에 저장.
    public String createRefreshToken(String email) {

        return jwtTokenProvider.createRefreshToken(email);
    }
}
