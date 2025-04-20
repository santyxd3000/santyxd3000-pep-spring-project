package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

//Repository injection
@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    //Use JPQL
    //JPQL Query to find account with username
    @Query("SELECT account FROM Account account WHERE account.username = :user") 
    Account findByUsername(@Param("user") String username);

    //JPQL Query to find Account by username and password
    @Query("SELECT account FROM Account account WHERE account.username = :user AND account.password = :pwd")
    Account findByUsernameAndPassword(@Param("user") String username, @Param("pwd") String password);

}
