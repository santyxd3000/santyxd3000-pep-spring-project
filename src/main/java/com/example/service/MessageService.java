package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    public final MessageRepository messageRepository;

    //Parametrized constructor for dependency injection
    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    //Creates a new message in the repository
    public Message createMessage(Message message){

        //Check business rules when creating a message
        if (message.getMessageText() != "" && message.getMessageText().length() < 255){
            return messageRepository.save(message);
        } 
        //Invalid by default
        return null;

    }

    //Get all messages from repository
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    //Get message from repository given its id
    public Optional<Message> getMessageById(Integer id) {
        return messageRepository.findById(id);
    }

    //Delete message given its id
    public int deleteMessageById(Integer id) {
        return messageRepository.deleteMessageById(id);
    }

    //Update message given its id and new message
    public int updateMessage(String new_message, Integer id) {
        //Print new message
        System.out.println("New Message is: " + new_message);
        //Check message business rules
        if (new_message.length() <= 255 && !new_message.isEmpty()){
            //Check if message belongs to any id
            if (messageRepository.findById(id) != null) {
                //Update message
                return messageRepository.updateMessage(new_message, id);
            }
        }

        //Returns -1 by default
        return -1;
    }

    //Get all message given a user id
    public List<Message> getMessageByUserId(Integer id) {
        return messageRepository.findMessagesByUserId(id);
    }



}
