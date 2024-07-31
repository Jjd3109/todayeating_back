package org.example.todayeating_back.dto.response;

import org.example.todayeating_back.entity.*;

import java.util.List;
import java.util.function.Supplier;

public record FindMyRoomsResponse(
        Long id,
        String roomName,
        String roomIntroduce,
        List<String> roomImagesPath,
        boolean openYn

) {
    public static FindMyRoomsResponse response(RoomAndMemberConnect roomAndMemberConnect) {
        Room room = roomAndMemberConnect.getRoom();
        return new FindMyRoomsResponse(
                roomAndMemberConnect.getId(),
                room.getRoomName(),
                room.getRoomIntroduce(),
                room.getImagePaths(),
                room.getOpenYn()
        );
    }
}