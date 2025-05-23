package org.example.todayeating_back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Map extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private String content;
    private String markerId;
    private String review;
    private double latitude;
    private double longitude;
    private double rating;

    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Images> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public static Map saveMap(String location, String content, String markerId, String review, double latitude, double longitude, double rating, Room room){
        Map map = new Map();
        map.location = location;
        map.content = content;
        map.markerId = markerId;
        map.review = review;
        map.latitude = latitude;
        map.longitude = longitude;
        map.rating = rating;
        map.room = room;
        return map;
    }

    public void addImage(Images image) {
        images.add(image);
        image.setMap(this);
    }

    public List<String> getImagePaths() {
        if (images == null || images.isEmpty()) {
            return Collections.emptyList();
        }
        return images.stream()
                .map(Images::getImagePath)
                .collect(Collectors.toList());
    }

}
