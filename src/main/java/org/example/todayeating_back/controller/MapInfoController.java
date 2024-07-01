package org.example.todayeating_back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.dto.request.MapInfo;
import org.example.todayeating_back.entity.Map;
import org.example.todayeating_back.service.MapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class MapInfoController {

    private final MapService mapService;

    @PostMapping("/save/MapInfo")
    public ResponseEntity<?> saveMapInfo(
            @RequestParam("location") String location,
            @RequestParam("content") String content,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("markerId") String markerId,
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude
    ) throws IOException {


        Map map = Map.saveMap(location, content, markerId, latitude, longitude);

        return ResponseEntity.ok().body(mapService.saveMap(map));
    }

    @GetMapping("/findMapInfo")
    public ResponseEntity<?> findMapInfo()  {

        return ResponseEntity.ok().body(mapService.findMap());
    }

    @PostMapping("/delete/MapInfo")
    public ResponseEntity<?> deleteMapInfo(@RequestParam("markerId") String markerId){

        mapService.deleteMap(markerId);
        return ResponseEntity.ok().body("");
    }
}
