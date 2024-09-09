package org.example.todayeating_back.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.dto.request.RoomEnterRequest;
import org.example.todayeating_back.entity.MemberInfo;
import org.example.todayeating_back.entity.Room;
import org.example.todayeating_back.entity.RoomAndMemberConnect;
import org.example.todayeating_back.exception.RoomAlreadyEnteredException;
import org.example.todayeating_back.repository.MemberInfoRepository;
import org.example.todayeating_back.repository.RAMCRepository;
import org.example.todayeating_back.repository.RoomRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RAMCService {

    private final RAMCRepository ramcRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final RoomRepository roomRepository;

    public RoomAndMemberConnect saveRoomAndMemberConenct(Authentication authentication, RoomEnterRequest roomEnterRequest)  {

        MemberInfo memberInfo = findMemberInfo(authentication);
        Room room = findRoom(roomEnterRequest);


        if (findRoomAndMemberInfo(memberInfo, room).isEmpty()) {
            return ramcRepository.save(RoomAndMemberConnect.saveRoomAndMemberConnect(memberInfo, room));
        } else {
            throw new RoomAlreadyEnteredException("이미 이 방에 참가하였습니다.");
        }


    }

    /*
     * 방에 접속 되어 있는 사람인가 확인
     */
    public List<RoomAndMemberConnect> findRoomAndMember(MemberInfo memberInfo){
        return ramcRepository.findByMemberInfo(memberInfo);
    }
    /*
     * 방찾기
     */
    public Room findRoom(RoomEnterRequest roomEnterRequest){
        return  roomRepository.findById(roomEnterRequest.id())
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 없습니다. roomId: " + roomEnterRequest.id()));
    }

    /*
     * findMember 회원 찾기
     */
    public MemberInfo findMemberInfo(Authentication authentication){
        return memberInfoRepository.findByEmail(authentication.getName()).orElseThrow();
    }

    public List<RoomAndMemberConnect> findRoomAndMemberInfo(MemberInfo memberInfo, Room room){
        return ramcRepository.findByMemberInfoAndAndRoom(memberInfo, room);
    }




}
