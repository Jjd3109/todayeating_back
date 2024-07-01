package org.example.todayeating_back.repository;

import org.example.todayeating_back.entity.Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MapRepository extends JpaRepository<Map, Long> {

    Map findByMarkerId(String markerId);
}
