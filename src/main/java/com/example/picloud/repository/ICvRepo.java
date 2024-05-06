package com.example.picloud.repository;

import com.example.picloud.entities.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICvRepo extends JpaRepository<Cv,Long> {
}
