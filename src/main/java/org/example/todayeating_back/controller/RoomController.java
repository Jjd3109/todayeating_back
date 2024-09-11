package org.example.todayeating_back.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.dto.request.RoomEnterRequest;
import org.example.todayeating_back.dto.request.RoomPassoword;
import org.example.todayeating_back.dto.request.RoomRequest;
import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.service.RAMCService;
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
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;

    /*
     * 방 생성
     */
    @PostMapping("/api/v1/save/room")
    public ResponseEntity<?> saveRoom(@ModelAttribute RoomRequest roomRequest) {
        try{
            return ResponseEntity.ok().body(roomService.saveRoom(roomRequest));
        }catch(NoSuchElementException n){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한이 없습니다.");
        }
   }


    /*
     * 현재 생성되어 있는 모든 방
     */
    @GetMapping("/api/v1/find/rooms")
    public ResponseEntity<?> findRooms(@RequestParam("page") int page, @RequestParam("size") int size){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
            return ResponseEntity.ok().body(roomService.findRooms(page, size, authentication));
        }catch(NoSuchElementException n){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한이 없습니다.");
        }
     }

    /*
     * 내가 들어가 있는 방
     */
    @GetMapping("/api/v1/find/myRooms")
    public ResponseEntity<?> findMyRooms(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
            return ResponseEntity.ok().body(roomService.findMyRomms(authentication));
        }catch(NoSuchElementException n){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한이 없습니다.");
        }
   }

    @GetMapping("/api/v1/find/roomPassword")
    public ResponseEntity<?> findPassword(@RequestBody RoomPassoword roomPassoword){
        try{
            return ResponseEntity.ok().body(roomService.checkPassword(roomPassoword));
        }catch(NoSuchElementException n){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한이 없습니다.");
        }
    }
}
