package com.example.btcsedge.repo;



import com.example.btcsedge.domain.model.AnprEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnprEventRepo extends JpaRepository<AnprEvent, Long> {}
