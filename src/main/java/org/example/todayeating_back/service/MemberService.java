package org.example.todayeating_back.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.config.JwtTokenProvider;
import org.example.todayeating_back.dto.request.MemberInfoRequest;
import org.example.todayeating_back.dto.response.FindEmail;
import org.example.todayeating_back.dto.response.FindEmailAll;
import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.repository.MemberInfoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberInfoRepository memberInfoRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberInfo memberInfo = memberInfoRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return memberInfo;
    }

    /*
     * 현재 일시적으로 USER권한 주고 있는 상태
     */
    public MemberInfo saveMember(MemberInfoRequest memberInfoRequest) {
        //List<String> roles = new ArrayList<>();
        //roles.add("ROLE_USER"); // 일시적으로 USER 권한 넣어 놓기.
        String nickName = randomMakeNickName();

        MemberInfo memberInfo = MemberInfo.saveMember(memberInfoRequest.email(), passwordEncoder.encode(memberInfoRequest.password()), nickName, "ROLE_USER");
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

    public List<FindEmail> findEmail(String email) throws Exception {
        List<FindEmail> results = memberInfoRepository.findByEmail(email).stream()
                .map(FindEmail::response)
                .collect(Collectors.toList());

        // 결과가 있으면 반환 !
        if (results.isEmpty()) {
            return results;
        }

        throw new Exception("Email exist: ");
    }

    public List<FindEmailAll> findEmailAll(String email) throws Exception {
        List<FindEmailAll> results = memberInfoRepository.findByEmail(email).stream()
                .map(FindEmailAll::response)
                .collect(Collectors.toList());

        // 결과가 있으면 반환 !
        if (results.isEmpty()) {
            throw new Exception("Email exist: ");
        }
        return results;
}

    /*
     * 회원 닉네임 자동 생성
     */

    public String randomMakeNickName(){
        boolean result = true;
        String value = "";

        while(result){
            Random random = new Random();
            String[] firstName = {"맛있는", "재밌는", "세상에서", "최고의", "너는"};
            String[] twoName = {"음식", "짜장면", "음식추천", "천재", "금지"};
            int threeName = random.nextInt(5000);

            value = firstName[random.nextInt(firstName.length)] + twoName[random.nextInt(twoName.length)] + threeName;

            if(memberInfoRepository.findByNickName(value).isEmpty()){
                result = false;
            }
        }

        return value;
    }

    public boolean updateMember(MemberInfoRequest memberInfoRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        MemberInfo memberInfo = memberInfoRepository.findByEmail(authentication.getName()).orElseThrow();
        memberInfo.updateMember(memberInfoRequest.nickName());

        return true;
    }
}
