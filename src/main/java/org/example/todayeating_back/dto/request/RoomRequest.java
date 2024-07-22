package org.example.todayeating_back.dto.request;

import lombok.ToString;

public record RoomRequest(
        String roomName,
        String roomPassword,
        String roomIntroduce,
        boolean openYn

) {
}
