package org.example.todayeating_back.dto.response;

import org.example.todayeating_back.entity.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

public record FindMyRoomsResponse(
        Long id,
        String roomName,
        String roomIntroduce,
        boolean openYn,
        LocalDateTime createdDate,
        Long roomId

) {
    public static FindMyRoomsResponse response(RoomAndMemberConnect roomAndMemberConnect) {
        Room room = roomAndMemberConnect.getRoom();
        return new FindMyRoomsResponse(
                roomAndMemberConnect.getId(),
                room.getRoomName(),
                room.getRoomIntroduce(),
                room.getOpenYn(),
                room.getCreatedDate(),
                room.getId()
        );
    }
}