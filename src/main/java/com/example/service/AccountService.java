package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    //Parametrized constructor
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    //Register function returning response depending on situation
    public int register(Account account){
        if(account.getUsername() != "" && account.getPassword().length() >= 4)
        {
            if(accountRepository.findByUsername(account.getUsername()) == null)
            {
                //Message successful
                accountRepository.save(account); 
                return 200; 
            }
            else
                //Client server error
                return 409;
        }
        //Invalid credentials error
        return 400; 
    }

    //Login by finding username and password
    public Account login(Account account) {
        //Find account if username and password exist
        return accountRepository.findByUsernameAndPassword(account.getUsername(),account.getPassword());
    }

    //Find user by id
    public Optional<Account> findByUserId(Integer id){
        return accountRepository.findById(id);
    }
}
