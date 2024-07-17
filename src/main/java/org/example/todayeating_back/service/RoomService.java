package org.example.todayeating_back.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.dto.request.RoomRequest;
import org.example.todayeating_back.dto.response.RoomResponse;
import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.entity.Room;
import org.example.todayeating_back.entity.RoomAndMemberConnect;
import org.example.todayeating_back.repository.RAMCRepository;
import org.example.todayeating_back.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final RAMCRepository ramcRepository;

    public Room saveRoom(RoomRequest roomRequest, MemberInfo memberInfo){
        Room room = Room.saveRoom(roomRequest.roomName(), roomRequest.roomPassword(), roomRequest.openYn(), memberInfo);
        RoomAndMemberConnect ramc = RoomAndMemberConnect.saveRoomAndMemberConnect(memberInfo, room);

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

}
