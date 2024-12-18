package com.example.AccountService.Repo;

import com.example.AccountService.Model.MailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<MailInfo, Long> {

    List<MailInfo> findAllByStatus(boolean status);
}
