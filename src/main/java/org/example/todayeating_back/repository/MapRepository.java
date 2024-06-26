package org.example.todayeating_back.repository;

import org.example.todayeating_back.entity.MapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<MapEntity, Long> {
}
