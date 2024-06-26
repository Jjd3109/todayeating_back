package org.example.todayeating_back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private String content;
    private String markerId;
    private double latitude;
    private double longitude;

    public static MapEntity saveMap(String location, String content, String markerId, double latitude, double longitude){
        MapEntity mapEntity = new MapEntity();
        mapEntity.location = location;
        mapEntity.content = content;
        mapEntity.markerId = markerId;
        mapEntity.latitude = latitude;
        mapEntity.longitude = longitude;
        return mapEntity;
    }

}
