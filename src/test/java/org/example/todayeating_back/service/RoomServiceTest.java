package org.example.todayeating_back.service;

import jakarta.transaction.Transactional;
import org.example.todayeating_back.config.JwtTokenProvider;
import org.example.todayeating_back.dto.request.MemberInfoRequest;
import org.example.todayeating_back.dto.request.RoomRequest;
import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.entity.Room;
import org.example.todayeating_back.entity.RoomAndMemberConnect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Member;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    RoomService roomService;

    @Autowired
    RAMCService ramcService;

    private MemberInfo memberInfo;
    private Room testRoom;

    @BeforeEach
    void 회원생성_및_방생성() {
        // given
        String email = "test@test.co.kr";
        String password = "1234";
        String nickName = "정두";

        MemberInfoRequest memberInfoRequest = new MemberInfoRequest(email, password, nickName);


        // 회원가입
        memberInfo = memberService.saveMember(memberInfoRequest);

        String roomName = "roomNameTest";
        String roomPassword = "1234";
        boolean openYn = true;
        RoomRequest roomRequest = new RoomRequest(roomName, roomPassword, openYn);

        String roomNameTwo = "roonNameTest";
        RoomRequest roomRequest2 = new RoomRequest(roomNameTwo, roomPassword, openYn);



        // 방 생성 및 방과 회원의 연결 생성
        testRoom = roomService.saveRoom(roomRequest, memberInfo);
        Room testRoom2 = roomService.saveRoom(roomRequest2, memberInfo);
        ramcService.saveRoomAndMemberConenct(memberInfo, testRoom);
        ramcService.saveRoomAndMemberConenct(memberInfo, testRoom2);
    }

    @Test
    @Transactional
    public void 방_생성_및_연결_생성() {
        // then
        assertThat(testRoom.getRoomName()).isEqualTo("roomNameTest");
        System.out.println(testRoom);
    }

    @Test
    @Transactional
    public void 생성_된_방_확인() {
        //when
        List<RoomAndMemberConnect> roomAndMemberConnectList =  ramcService.findRoomAndMember(memberInfo);

        // then
        System.out.println(roomAndMemberConnectList);

    }




}