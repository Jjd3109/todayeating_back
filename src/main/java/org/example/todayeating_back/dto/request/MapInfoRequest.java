package org.example.todayeating_back.dto.request;

public record MapInfoRequest(
        Long id,
        String location,
        String content,
        String image, // 바이트 배열이나 저장된 이미지의 URL로 처리할 수 있습니다.
        String markerId,
        double latitude,
        double longitude
) {}
