package org.example.todayeating_back.service;

import lombok.RequiredArgsConstructor;
import org.example.todayeating_back.entity.MapEntity;
import org.example.todayeating_back.repository.MapRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapService {

    private final MapRepository mapRepository;

    public MapEntity saveMap(MapEntity mapEntity){
        return mapRepository.save(mapEntity);
    }

}
