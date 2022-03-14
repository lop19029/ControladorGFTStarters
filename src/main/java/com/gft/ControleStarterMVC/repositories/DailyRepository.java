package com.gft.ControleStarterMVC.repositories;

import com.gft.ControleStarterMVC.entities.Daily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyRepository extends JpaRepository<Daily, Long> {
}
