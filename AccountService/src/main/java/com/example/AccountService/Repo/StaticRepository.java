package com.example.AccountService.Repo;

import com.example.AccountService.Model.StatisticDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaticRepository extends JpaRepository<StatisticDTO,Long> {

    List<StatisticDTO> findAllByStatus(boolean status);
}
