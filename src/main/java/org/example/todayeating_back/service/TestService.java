package org.example.todayeating_back.service;

import lombok.RequiredArgsConstructor;
import org.example.todayeating_back.entity.TestEntity;
import org.example.todayeating_back.repository.TestRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public TestEntity test(){
        return testRepository.findById(1L).orElseThrow();
    }


}
