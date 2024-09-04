package org.example.todayeating_back.repository;

import org.example.todayeating_back.entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {

     Optional<MemberInfo> findByEmail(String email);

     Optional<MemberInfo> findByNickName(String nickname);

}
