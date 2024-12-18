package org.example.statictisservice.Repository;

import org.example.statictisservice.Entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Integer> {
}
