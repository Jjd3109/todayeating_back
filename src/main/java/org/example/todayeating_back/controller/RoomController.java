package org.example.todayeating_back.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.dto.request.RoomRequest;
import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/api/v1/save/room")
    public ResponseEntity<?> saveRoom(@RequestBody RoomRequest roomRequest, HttpServletRequest request) {
        MemberInfo memberInfo = (MemberInfo) request.getAttribute("memberInfo");

        if (memberInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().body(roomService.saveRoom(roomRequest, memberInfo));
    }

    /*
     * 현재 생성되어 있는 모든 방
     */
    @GetMapping("/api/v1/find/rooms")
    public ResponseEntity<?> findRooms(@RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok().body(roomService.findRooms(page, size));
    }
}
