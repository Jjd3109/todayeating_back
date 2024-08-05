package org.example.todayeating_back.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.dto.response.FindMapInfoResponse;
import org.example.todayeating_back.entity.Images;
import org.example.todayeating_back.entity.Map;
import org.example.todayeating_back.entity.Room;
import org.example.todayeating_back.repository.MapRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MapService {

    private final MapRepository mapRepository;

    public FindMapInfoResponse saveMapWithImages(Map map, List<MultipartFile> imageFiles) throws IOException {

            try {
                for(MultipartFile imageFile : imageFiles){
                    if (!imageFiles.isEmpty()) {
                        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                        Path filePath = Paths.get("C:/Users/정종두/Desktop/테스트/" + fileName);
                        //Path filePath = Paths.get("C:/Users/JD/Desktop/새 폴더/" + fileName);
                        Files.copy(imageFile.getInputStream(), filePath);

                        Images image = Images.builder()
                                .imagePath(filePath.toString())
                                .build();

                        map.addImage(image);
                    }
                }
            }catch (NullPointerException n){
                return FindMapInfoResponse.from(mapRepository.save(map));
            }

        return FindMapInfoResponse.from(mapRepository.save(map));
    }

    public List<FindMapInfoResponse> findMap(Room room) {
        return mapRepository.findByRoom(room).stream()
                .map(FindMapInfoResponse::from) // Map -> FindMapInfoResponse 변환
                .toList();
    }

    public void deleteMap(String markerId) {
        Map map = mapRepository.findByMarkerId(markerId);
        mapRepository.delete(map);

    }
}
