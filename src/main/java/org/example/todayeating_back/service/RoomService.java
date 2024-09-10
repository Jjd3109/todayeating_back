package org.example.todayeating_back.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.dto.request.RoomRequest;
import org.example.todayeating_back.dto.response.FindMapInfoResponse;
import org.example.todayeating_back.dto.response.FindMyRoomsResponse;
import org.example.todayeating_back.dto.response.RoomResponse;
import org.example.todayeating_back.entity.*;
import org.example.todayeating_back.repository.MemberInfoRepository;
import org.example.todayeating_back.repository.RAMCRepository;
import org.example.todayeating_back.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final RAMCRepository ramcRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean saveRoom(RoomRequest roomRequest){
        //성공시 true
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            MemberInfo memberInfo = loadMemberInfo(authentication.getName());


            Room room = Room.saveRoom(roomRequest.roomName(), passwordEncoder.encode(roomRequest.roomPassword()), roomRequest.roomIntroduce(), roomRequest.openYn(), memberInfo);
            RoomAndMemberConnect ramc = RoomAndMemberConnect.saveRoomAndMemberConnect(memberInfo, room);



            Room returnRoom = roomRepository.save(room);
            RoomAndMemberConnect returnRamc = ramcRepository.save(ramc); // 방 생성 할 때 그 방의 참여자 repository도 생성
            return true;
        } catch (Exception e){
            return false;
        }

    }

    /*
     * v1 LEFT JOIN FETCH를 이용한 페이징
     */

    public List<RoomResponse> findRooms(int page, int size, Authentication authentication) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        //1. memberInfo 값 가져오기
        MemberInfo memberInfo = findMemberInfo(authentication);
        List<RoomAndMemberConnect> roomAndMemberConnectList = findRoomAndMember(memberInfo);


        return roomRepository.findAllWithoutConnects(pageable, memberInfo).stream()
                .map(RoomResponse::response)
                .collect(Collectors.toList());
    }

    /*
     * v2 Entity Graph를 이용한 페이징
     */


//    public List<RoomResponse> findRooms(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//
//        return roomRepository.findAllWithMemberInfo(pageable).stream()
//                .map(RoomResponse::response)
//                .collect(Collectors.toList());
//    }

    public MemberInfo loadMemberInfo(String username){
        MemberInfo memberInfo = memberInfoRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return memberInfo;
    }


    public List<FindMyRoomsResponse> findMyRomms(Authentication authentication) {
        MemberInfo memberInfo = loadMemberInfo(authentication.getName());
        return ramcRepository.findByMemberInfo(memberInfo).stream()
                .map(FindMyRoomsResponse::response)
                .collect(Collectors.toList());

    }


    public Room findRoom(Long id){
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Map이 없습니다."));
        return  room;
    }


    /*
     * 이메일로 회원 아이디 조회
     */
    public MemberInfo findMemberInfo(Authentication authentication){
        return memberInfoRepository.findByEmail(authentication.getName()).orElseThrow();
    }


    /*
     * 방에 접속 되어 있는 사람인가 확인
     */
    public List<RoomAndMemberConnect> findRoomAndMember(MemberInfo memberInfo){
        return ramcRepository.findByMemberInfo(memberInfo);
    }
}

/*
 * 이미지 넣기 위한 것
 */

//        try {
//            for(MultipartFile imageFile : images){
//                if (!images.isEmpty()) {
//                    String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
//                    Path filePath = Paths.get("C:/Users/정종두/Desktop/테스트/" + fileName);
//                    //Path filePath = Paths.get("C:/Users/JD/Desktop/새 폴더/" + fileName);
//                    Files.copy(imageFile.getInputStream(), filePath);
//
//                    RoomImages image = RoomImages.builder()
//                            .imagePath(filePath.toString())
//                            .build();
//
//                    room.addImage(image);
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
