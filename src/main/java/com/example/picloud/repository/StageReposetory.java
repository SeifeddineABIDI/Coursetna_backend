package com.example.picloud.repository;

import com.example.picloud.entities.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageReposetory extends JpaRepository <Stage,Integer> {

    List<Stage> findByTitleContaining(String title);



}
