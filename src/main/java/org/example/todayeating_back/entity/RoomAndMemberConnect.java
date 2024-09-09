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
public class RoomAndMemberConnect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberInfo_id")
    @JsonIgnore
    private MemberInfo memberInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Room_id")
    @JsonIgnore
    private Room room;

    public static RoomAndMemberConnect saveRoomAndMemberConnect(MemberInfo memberInfo, Room room){
        RoomAndMemberConnect roomAndMemberConnect = new RoomAndMemberConnect();
        roomAndMemberConnect.memberInfo = memberInfo;
        roomAndMemberConnect.room =room;
        return roomAndMemberConnect;
    }
}
