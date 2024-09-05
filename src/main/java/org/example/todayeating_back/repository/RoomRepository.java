package org.example.todayeating_back.repository;

import org.example.todayeating_back.entity.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r " +
            "LEFT JOIN FETCH r.memberInfo " +
            "WHERE r.memberInfo.email != :email"
    )
    List<Room> findAllWithMember(Pageable pageable, @Param("email") String email);
}