package org.example.todayeating_back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.dto.request.RoomEnterRequest;
import org.example.todayeating_back.service.RAMCService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RAMCController {

    private final RAMCService ramcService;

    /*
     * 방 참가
     */
    @PostMapping("/api/v1/save/ramc")
    public ResponseEntity<?> enterRoom(@RequestBody RoomEnterRequest roomEnterRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok().body(ramcService.saveRoomAndMemberConenct(authentication, roomEnterRequest));
    }

}
