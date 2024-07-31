package org.example.todayeating_back.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.dto.request.RoomRequest;
import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/api/v1/save/room")
    public ResponseEntity<?> saveRoom(@ModelAttribute RoomRequest roomRequest,  @RequestParam(value = "images", required = false) List<MultipartFile> images) {
        return ResponseEntity.ok().body(roomService.saveRoom(roomRequest, images));
    }

    /*
     * 현재 생성되어 있는 모든 방
     */
    @GetMapping("/api/v1/find/rooms")
    public ResponseEntity<?> findRooms(@RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok().body(roomService.findRooms(page, size));
    }

    /*
     * 내가 들어가 있는 방
     */
    @GetMapping("/api/v1/find/myRooms")
    public ResponseEntity<?> findMyRooms(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("roomService.findMyRomms(authentication 값 = {}", roomService.findMyRomms(authentication).toString());

        return ResponseEntity.ok().body(roomService.findMyRomms(authentication));
    }
}
