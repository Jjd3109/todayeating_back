package org.example.todayeating_back.dto.response;

import org.example.todayeating_back.entity.Map;

import java.util.List;

public record FindMapInfoResponse(
        Long id,
        String location,
        String content,
        String markerId,
        String review,
        double latitude,
        double longitude,
        double rating,
        List<String> imagePath
) {
    // 생성자를 추가할 수 있으며, 주로 필드 초기화 등에 사용됩니다.
    public static FindMapInfoResponse from(Map map) {
        return new FindMapInfoResponse(
                map.getId(),
                map.getLocation(),
                map.getContent(),
                map.getMarkerId(),
                map.getReview(),
                map.getLatitude(),
                map.getLongitude(),
                map.getRating(),
                map.getImagePaths()
        );
    }

}