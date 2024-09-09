package org.example.todayeating_back.repository;

import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.entity.Room;
import org.example.todayeating_back.entity.RoomAndMemberConnect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RAMCRepository extends JpaRepository<RoomAndMemberConnect, Long> {

    @Query("SELECT DISTINCT r FROM RoomAndMemberConnect r " +
            "JOIN FETCH r.memberInfo m " +
            "JOIN FETCH r.room ro " +
            "WHERE r.memberInfo = :memberInfo")
    List<RoomAndMemberConnect> findByMemberInfo(@Param("memberInfo") MemberInfo memberInfo);

    List<RoomAndMemberConnect> findByMemberInfoAndAndRoom(MemberInfo memberInfo, Room room);
}
