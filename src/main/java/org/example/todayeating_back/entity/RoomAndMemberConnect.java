package org.example.todayeating_back.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class RoomAndMemberConnect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberInfo_id")
    private MemberInfo memberInfo;

    @OneToMany(mappedBy = "roomAndMemberConnect", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> room = new ArrayList<>();
}
