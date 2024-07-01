package org.example.todayeating_back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private String content;
    private String markerId;
    private double latitude;
    private double longitude;

    public static Map saveMap(String location, String content, String markerId, double latitude, double longitude){
        Map map = new Map();
        map.location = location;
        map.content = content;
        map.markerId = markerId;
        map.latitude = latitude;
        map.longitude = longitude;
        return map;
    }

}
