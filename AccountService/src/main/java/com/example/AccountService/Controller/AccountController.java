package com.example.AccountService.Controller;

import com.example.AccountService.Model.AccountDTO;
import com.example.AccountService.Model.AccountEntity;
import com.example.AccountService.Model.MailInfo;

import com.example.AccountService.Model.StatisticDTO;
import com.example.AccountService.Repo.AccountRepository;
import com.example.AccountService.Repo.MailRepository;
import com.example.AccountService.Repo.StaticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/account")
@Async
public class AccountController {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MailRepository messageRepository;

    @Autowired
    StaticRepository staticRepository;

    @PostMapping
    public AccountDTO createAccount(@RequestBody AccountDTO accountDTO) {
        StatisticDTO statisticDTO = StatisticDTO.builder()
                .message("Account" + accountDTO.getEmail() + "is Created")
                .createdDate(new Date())
                .build();
        staticRepository.save(statisticDTO);

        MailInfo mail = new MailInfo();
        mail.setTo(accountDTO.getEmail());
        mail.setToName(accountDTO.getName());
        mail.setSubject("Welcom to BitzNomad");
        mail.setContent("Hello Bitz Nomad");
        mail.setStatus(false);
        messageRepository.save(mail);
        accountRepository.save(AccountEntity.builder()
                        .email(accountDTO.getEmail())
                        .name(accountDTO.getName())
                .build());
//        kafkaTemplate.send("notification", mail);
//        kafkaTemplate.send("statistic", accountDTO);

        for (int i = 0; i < 100; i++) {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("notification", mail);
            CompletableFuture<SendResult<String, Object>> future1 = kafkaTemplate.send("statistic", statisticDTO);

            // Add a callback to handle success and failure scenarios using CompletableFuture API
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    // Handle success, log partition info
                    System.out.println("Message sent successfully to partition: " + result.getRecordMetadata().partition());
                } else {
                    // Handle failure, log error
                    System.err.println("Message failed to send: " + ex.getMessage());
                }
            });

            future1.whenComplete((result, ex) -> {
                if (ex == null) {
                    // Handle success, log partition info
                    System.out.println("Message sent successfully to partition: " + result.getRecordMetadata().partition());
                }else {
                    // Handle failure, log error
                    System.err.println("Message failed to send: " + ex.getMessage());
                }
            });
        }

        return accountDTO;
    }
}
