package org.example.todayeating_back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.dto.response.FindMapInfoResponse;
import org.example.todayeating_back.entity.Map;
import org.example.todayeating_back.entity.Room;
import org.example.todayeating_back.service.MapService;
import org.example.todayeating_back.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class MapInfoController {

    private final MapService mapService;
    private final RoomService roomService;

    @PostMapping("/save/mapInfo")
    public ResponseEntity<?> saveMapInfo(
            @RequestParam("location") String location,
            @RequestParam("content") String content,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam("markerId") String markerId,
            @RequestParam("review") String review,
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("rating") double rating,
            @RequestParam("roomId") Long roomId

    ) throws IOException {

        Room room = roomService.findRoom(roomId);

        Map map = Map.saveMap(location, content, markerId, review, latitude, longitude, rating, room);

        FindMapInfoResponse savedMap = mapService.saveMapWithImages(map, images);

        return ResponseEntity.ok().body(savedMap);
    }

    @GetMapping("/find/mapInfo/{id}")
    public ResponseEntity<?> findMapInfo(@PathVariable Long id) {
        Room room = roomService.findRoom(id);

        return ResponseEntity.ok().body(mapService.findMap(room));
    }

    @PostMapping("/delete/mapInfo")
    public ResponseEntity<?> deleteMapInfo(@RequestParam("markerId") String markerId) {

        mapService.deleteMap(markerId);
        return ResponseEntity.ok().body("");
    }


}
