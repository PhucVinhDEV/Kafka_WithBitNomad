package org.example.statictisservice.Service;

import lombok.extern.slf4j.Slf4j;
import org.example.statictisservice.Entity.Statistic;
import org.example.statictisservice.Repository.StatisticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StatictisService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StatisticRepository statisticRepo;

    @KafkaListener(id = "statisticGroup", topics = "statistic")
    public void listen(Statistic statistic) {
        logger.info("Received: " + statistic.getMessage());
        statisticRepo.save(statistic);
//		throw new RuntimeException("failed");
    }

    @KafkaListener(id = "dltGroup3", topics = "statistic.DLT")
    public void dltListen(String in) {
        logger.info("Received from DLT: ");
        System.out.println(in);
    }
}
