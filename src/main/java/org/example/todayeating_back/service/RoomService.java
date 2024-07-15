package org.example.todayeating_back.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.dto.request.RoomRequest;
import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.entity.Room;
import org.example.todayeating_back.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;


    public Room saveRoom(RoomRequest roomRequest, MemberInfo memberInfo){
        Room room = Room.saveRoom(roomRequest.roomName(), roomRequest.roomPassword(), memberInfo);

        return roomRepository.save(room);
    }



}
