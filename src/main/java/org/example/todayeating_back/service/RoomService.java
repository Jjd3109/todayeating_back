package org.example.todayeating_back.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.dto.request.RoomRequest;
import org.example.todayeating_back.dto.response.FindMapInfoResponse;
import org.example.todayeating_back.dto.response.RoomResponse;
import org.example.todayeating_back.entity.*;
import org.example.todayeating_back.repository.MemberInfoRepository;
import org.example.todayeating_back.repository.RAMCRepository;
import org.example.todayeating_back.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final RAMCRepository ramcRepository;
    private final MemberInfoRepository memberInfoRepository;

    public Room saveRoom(RoomRequest roomRequest,  @RequestParam(value = "images", required = false) List<MultipartFile> images){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberInfo memberInfo = loadMemberInfo(authentication.getName());


        Room room = Room.saveRoom(roomRequest.roomName(), roomRequest.roomPassword(), roomRequest.roomIntroduce(), roomRequest.openYn(), memberInfo);
        RoomAndMemberConnect ramc = RoomAndMemberConnect.saveRoomAndMemberConnect(memberInfo, room);

        try {
            for(MultipartFile imageFile : images){
                if (!images.isEmpty()) {
                    String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                    Path filePath = Paths.get("C:/Users/정종두/Desktop/테스트/" + fileName);
                    //Path filePath = Paths.get("C:/Users/JD/Desktop/새 폴더/" + fileName);
                    Files.copy(imageFile.getInputStream(), filePath);

                    RoomImages image = RoomImages.builder()
                            .imagePath(filePath.toString())
                            .build();

                    room.addImage(image);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Room returnRoom = roomRepository.save(room);
        RoomAndMemberConnect returnRamc = ramcRepository.save(ramc); // 방 생성 할 때 그 방의 참여자 repository도 생성

        return returnRoom;
    }

    /*
     * v1 LEFT JOIN FETCH
     */

    public List<RoomResponse> findRooms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return roomRepository.findAllWithMember(pageable).stream()
                .map(RoomResponse::response)
                .collect(Collectors.toList());
    }

    /*
     * v2 Entity Graph
     */


//    public List<RoomResponse> findRooms(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//
//        return roomRepository.findAllWithMemberInfo(pageable).stream()
//                .map(RoomResponse::response)
//                .collect(Collectors.toList());
//    }

    public MemberInfo loadMemberInfo(String username){
        MemberInfo memberInfo = memberInfoRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return memberInfo;
    }




}
