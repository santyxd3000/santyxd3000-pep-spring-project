package com.example.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    //Parametrized constructor for dependency injection
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    //Endpoint that handles register action, using a POST Request
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Account account) {
        return ResponseEntity.status(HttpStatus.valueOf(accountService.register(account))).build();
    }

    //Endpoint that handles login action, using a POST Request
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Account account) {
        Account act_account = accountService.login(account);
        if (act_account == null) {
            //If account does not exists, throws unauthorized request
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else{
            //Throw request successful and use account
            return ResponseEntity.status(HttpStatus.OK).body(act_account);
        }
    }

}
