package org.example.todayeating_back.dto.response;

import org.example.todayeating_back.entity.Map;
import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.entity.Room;
import org.example.todayeating_back.entity.RoomAndMemberConnect;

import java.time.LocalDateTime;
import java.util.List;

public record RoomResponse(
        Long id,
        String roomName,
        String roomPassword,
        String saveName,
        boolean openYn,
        LocalDateTime createdDate


) {
    public static RoomResponse response(Room room){
        return new RoomResponse(
                room.getId(),
                room.getRoomName(),
                room.getRoomPassword(),
                room.getMemberInfo().getNickName(),
                room.getOpenYn(),
                room.getCreatedDate()

        );
    }

}
