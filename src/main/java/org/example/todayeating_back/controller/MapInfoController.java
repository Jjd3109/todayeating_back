package org.example.todayeating_back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.dto.request.MapInfo;
import org.example.todayeating_back.dto.response.FindMapInfo;
import org.example.todayeating_back.entity.Map;
import org.example.todayeating_back.service.ImagesService;
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
    private final ImagesService imagesService;

    @PostMapping("/save/MapInfo")
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

        for(MultipartFile multipartFile : images){
            log.info("테스트");
            log.info("multipartFile 값 = {}", multipartFile.getOriginalFilename());
        }
        Map map = Map.saveMap(location, content, markerId, review, latitude, longitude, rating);
        FindMapInfo savedMap = mapService.saveMapWithImages(map, images);

        return ResponseEntity.ok().body(savedMap);
    }

    @GetMapping("/findMapInfo")
    public ResponseEntity<?> findMapInfo() {
        return ResponseEntity.ok().body(mapService.findMap());
    }

    @PostMapping("/delete/MapInfo")
    public ResponseEntity<?> deleteMapInfo(@RequestParam("markerId") String markerId) {

        mapService.deleteMap(markerId);
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/save/image")
    public ResponseEntity<?> saveImage(@RequestParam("image") MultipartFile image) throws IOException {

        // 파일 저장 로직 추가
        // 예: File 저장, 데이터베이스 저장 등

        return ResponseEntity.ok().body(imagesService.saveImage(image));
    }
}
