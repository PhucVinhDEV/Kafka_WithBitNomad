package com.example.AccountService.Service;


import com.example.AccountService.Model.MailInfo;
import com.example.AccountService.Model.StatisticDTO;
import com.example.AccountService.Repo.MailRepository;
import com.example.AccountService.Repo.StaticRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@AllArgsConstructor
public class PoolService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final MailRepository mailRepository;
    private final StaticRepository staticRepository;



    @Scheduled(fixedRate = 5000)
    public void producer() {
        List<MailInfo> messageDTOs = mailRepository.findAllByStatus(false);

        for (MailInfo messageDTO : messageDTOs) {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("notification", messageDTO);

            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    // Handle failure
                    log.error("FAIL: {}", ex.getMessage(), ex);
                } else {
                    // Handle success
                    log.info("SUCCESS: Topic: {}, Partition: {}, Offset: {}",
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());

                    messageDTO.setStatus(true); // Success
                    mailRepository.save(messageDTO);
                }
            });
        }
        List<StatisticDTO> statisticDTOs = staticRepository.findAllByStatus(false);

        for (StatisticDTO statisticDTO : statisticDTOs) {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("statistic", statisticDTO);

            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    // Handle failure
                    log.error("FAIL for Statistic ID {}: {}", statisticDTO.getId(), ex.getMessage(), ex);
                } else {
                    // Handle success
                    log.info("SUCCESS for Statistic ID {}: Topic: {}, Partition: {}, Offset: {}",
                            statisticDTO.getId(),
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());

                    statisticDTO.setStatus(true); // Success
                    staticRepository.save(statisticDTO);
                }
            });
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void delete() {
        List<MailInfo> messageDTOs = mailRepository.findAllByStatus(true);
        mailRepository.deleteAllInBatch(messageDTOs);
        List<StatisticDTO> statisticDTOs = staticRepository.findAllByStatus(true);
        staticRepository.deleteAllInBatch(statisticDTOs);
    }
}
