package org.example.todayeating_back.service;

import lombok.RequiredArgsConstructor;
import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.entity.Room;
import org.example.todayeating_back.entity.RoomAndMemberConnect;
import org.example.todayeating_back.repository.RAMCRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RAMCService {

    private final RAMCRepository ramcRepository;

    public RoomAndMemberConnect saveRoomAndMemberConenct(MemberInfo memberInfo, Room room){
        return ramcRepository.save(RoomAndMemberConnect.saveRoomAndMemberConnect(memberInfo, room));
    }

    public List<RoomAndMemberConnect> findRoomAndMember(MemberInfo memberInfo){
        return ramcRepository.findByMemberInfo(memberInfo);
    }

}
