package org.example.todayeating_back.repository;

import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.entity.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE  r.id NOT IN (" +
            "SELECT rc.room.id FROM RoomAndMemberConnect rc WHERE rc.memberInfo = :memberInfo)")
    List<Room> findAllWithoutConnects(Pageable pageable, @Param("memberInfo") MemberInfo memberInfo);



    Optional<Room> findByIdAndRoomPassword(Long id, String roomPassword);
}