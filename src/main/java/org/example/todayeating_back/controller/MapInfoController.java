package org.example.todayeating_back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.dto.request.MapInfo;
import org.example.todayeating_back.dto.response.FindMapInfo;
import org.example.todayeating_back.entity.Map;
import org.example.todayeating_back.service.MapService;
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


    @PostMapping("/save/mapInfo")
    public ResponseEntity<?> saveMapInfo(
            @RequestParam("location") String location,
            @RequestParam("content") String content,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam("markerId") String markerId,
            @RequestParam("review") String review,
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("rating") double rating

    ) throws IOException {

        Map map = Map.saveMap(location, content, markerId, review, latitude, longitude, rating);
        FindMapInfo savedMap = mapService.saveMapWithImages(map, images);

        return ResponseEntity.ok().body(savedMap);
    }

    @GetMapping("/find/mapInfo")
    public ResponseEntity<?> findMapInfo() {
        return ResponseEntity.ok().body(mapService.findMap());
    }

    @PostMapping("/delete/mapInfo")
    public ResponseEntity<?> deleteMapInfo(@RequestParam("markerId") String markerId) {

        mapService.deleteMap(markerId);
        return ResponseEntity.ok().body("");
    }


}
