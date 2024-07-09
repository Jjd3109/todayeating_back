package org.example.todayeating_back.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final List<String> roles = new ArrayList<>();
    private final MemberInfoRepository memberInfoRepository;


    public MemberInfo saveMember(String email, String password) {
        roles.add("USER"); // 일시적으로 USER 권한 넣어 놓기.
        MemberInfo memberInfo = MemberInfo.saveMember(email, passwordEncoder.encode(password), roles);
        return memberInfoRepository.save(memberInfo);
    }

    /*
     * UsernameNotFoundException 에서 처리
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberInfo memberInfo = memberInfoRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("이러한 닉네임의 회원이 존재하지 않아용 : " + username));

        return new User(
                memberInfo.getEmail(),
                memberInfo.getPassword(),
                memberInfo.getAuthorities()  // Replace with actual authorities if available
        );
    }
}
