package org.example.todayeating_back.service;

import lombok.RequiredArgsConstructor;
import org.example.todayeating_back.dto.request.RoomEnterRequest;
import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.entity.Room;
import org.example.todayeating_back.entity.RoomAndMemberConnect;
import org.example.todayeating_back.repository.MemberInfoRepository;
import org.example.todayeating_back.repository.RAMCRepository;
import org.example.todayeating_back.repository.RoomRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RAMCService {

    private final RAMCRepository ramcRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final RoomRepository roomRepository;

    public RoomAndMemberConnect saveRoomAndMemberConenct(Authentication authentication, RoomEnterRequest roomEnterRequest){
        MemberInfo memberInfo = memberInfoRepository.findByEmail(authentication.getName()).orElseThrow();

        Room room = roomRepository.findById(roomEnterRequest.id())
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 없습니다. roomId: " + roomEnterRequest.id()));


        return ramcRepository.save(RoomAndMemberConnect.saveRoomAndMemberConnect(memberInfo, room));
    }

    public List<RoomAndMemberConnect> findRoomAndMember(MemberInfo memberInfo){
        return ramcRepository.findByMemberInfo(memberInfo);
    }
//
//    public List<RoomAndMemberConnect>

}
