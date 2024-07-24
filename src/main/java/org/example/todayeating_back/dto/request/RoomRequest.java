package org.example.todayeating_back.dto.request;

import lombok.ToString;

import java.util.List;

public record RoomRequest(
        String roomName,
        String roomPassword,
        String roomIntroduce,
        List<String> imagePath,
        boolean openYn

) {

}
