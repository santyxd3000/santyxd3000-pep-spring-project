package com.example.controller;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
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


    //Endpoint to create a new message using POST Request
    @PostMapping("/messages")
    public ResponseEntity createMessage(@RequestBody Message message) {

        //Check if account exists
        if (accountService.findByUserId(message.getPostedBy()).isPresent()) {
            Message new_message = messageService.createMessage(message);
            //Check if message created is not null, if it isn't, an OK Request is thrown
            if (new_message != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new_message);
            }
        }
        //Throws a bad request by default
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

        //Deleting Message By Id using DELETE Request
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity deleteMessageById(@PathVariable("messageId") Integer id) {
        int result = messageService.deleteMessageById(id);

        //If the result (rows deleted) is more than 0
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        //If no row was deleted, throw an OK Request with an empty message
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Endpoint to return all messages using GET request
    @GetMapping("/messages")
    public ResponseEntity getAllMessages(){
        //Throws OK Request and executes method
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessages());
    }


    //Get Messages By User ID, using GET Request
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity getMessagesByUserId(@PathVariable("accountId") Integer id){
        //Throw OK Request and Execute Method from Service in Body
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessageByUserId(id));
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity getMessageById(@PathVariable("messageId") Integer id){
        Optional<Message> message = messageService.getMessageById(id);

        //Checks if message retrieved exists and executes operation throwing successful message
        if (message.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }

        //Returns OK request, but empty message
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    //Update a Message, using a PATCH request
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity updateMessage(@RequestBody Message message,@PathVariable("messageId") Integer id){
        int result = messageService.updateMessage(message.getMessageText(), id);

        //If result (rows affected) is more than 0
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

        //If message not found, throw a bad request
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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


    //Endpoint that handles register action, using a POST Request
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Account account) {
        return ResponseEntity.status(HttpStatus.valueOf(accountService.register(account))).build();
    }

    


}
