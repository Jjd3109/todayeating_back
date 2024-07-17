package org.example.todayeating_back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    private String roomPassword;

    private Boolean openYn; // 공개 비공개 여부

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomAndMemberConnect> roomAndMemberConnect = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Map> map = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberinfo_id")
    @JsonIgnore
    private MemberInfo memberInfo;

    public static Room saveRoom(String roomName, String roomPassword, boolean openYn, MemberInfo memberInfo){
        Room room = new Room();
        room.roomName = roomName;
        room.roomPassword = roomPassword;
        room.openYn = openYn;
        room.memberInfo = memberInfo;
        return room;
    }

}
