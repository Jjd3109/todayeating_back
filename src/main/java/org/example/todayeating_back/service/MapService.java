package org.example.todayeating_back.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todayeating_back.entity.Map;
import org.example.todayeating_back.repository.MapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MapService {

    private final MapRepository mapRepository;

    public Map saveMap(Map map) {
        return mapRepository.save(map);
    }

    public List<Map> findMap() {
        return mapRepository.findAll();
    }

    public void deleteMap(String markerId) {
        Map map = mapRepository.findByMarkerId(markerId);
        mapRepository.delete(map);

    }
}
