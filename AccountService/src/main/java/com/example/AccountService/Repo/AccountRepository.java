package com.example.AccountService.Repo;

import com.example.AccountService.Model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Long> {
}
