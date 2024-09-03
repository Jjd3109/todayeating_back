package org.example.todayeating_back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    private String roomPassword;

    private String roomIntroduce;

    private Boolean openYn; // 공개 비공개 여부

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomAndMemberConnect> roomAndMemberConnect = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Map> map = new ArrayList<>();
//
//    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
//    @BatchSize(size = 10)
//    private List<RoomImages> roomImages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberinfo_id")
    private MemberInfo memberInfo;

    public static Room saveRoom(String roomName, String roomPassword, String roomIntroduce, boolean openYn, MemberInfo memberInfo){
        Room room = new Room();
        room.roomName = roomName;
        room.roomPassword = roomPassword;
        room.roomIntroduce = roomIntroduce;
        room.openYn = openYn;
        room.memberInfo = memberInfo;
        return room;
    }

//    public void addImage(RoomImages roomImage){
//        roomImages.add(roomImage);
//        roomImage.setRoom(this);
//    }
//
//    public List<String> getImagePaths() {
//        if (roomImages == null || roomImages.isEmpty()) {
//            return Collections.emptyList();
//        }
//        return roomImages.stream()
//                .map(RoomImages::getImagePath)
//                .collect(Collectors.toList());
//    }

}
