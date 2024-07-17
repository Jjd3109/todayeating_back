package org.example.todayeating_back.repository;

import org.example.todayeating_back.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @EntityGraph(attributePaths = {"memberInfo", "memberInfo.roles"})
    @Query("SELECT r FROM Room r")
    List<Room> findAllWithMemberInfo(Pageable pageable);



    @Query("SELECT r FROM Room r LEFT JOIN FETCH r.memberInfo")
    List<Room> findAllWithMember(Pageable pageable);
}