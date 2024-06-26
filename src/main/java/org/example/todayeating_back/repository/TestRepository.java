package org.example.todayeating_back.repository;

import org.example.todayeating_back.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
