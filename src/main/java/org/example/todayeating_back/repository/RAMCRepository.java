package org.example.todayeating_back.repository;

import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.entity.RoomAndMemberConnect;
import org.example.todayeating_back.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RAMCRepository extends JpaRepository<RoomAndMemberConnect, Long> {

    List<RoomAndMemberConnect> findByMemberInfo(MemberInfo memberInfo);
}
