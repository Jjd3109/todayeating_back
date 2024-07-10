package org.example.todayeating_back.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomAndMemberConnect_id")
    private RoomAndMemberConnect roomAndMemberConnect;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Map> map = new ArrayList<>();

}
