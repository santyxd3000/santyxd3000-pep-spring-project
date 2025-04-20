package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

//Repository injectionn
@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {

    //Modifying and transactional injection
    //JPQL Query to delete message by message id
    @Modifying
    @Transactional
    @Query("DELETE FROM Message message WHERE message.id = :id")
    int deleteMessageById(@Param("id") Integer id);

    //Update message given its id
    @Modifying
    @Transactional
    @Query("UPDATE Message message SET message.messageText = :text WHERE message.messageId = :id")
    int updateMessage(@Param("text") String newMessage, @Param("id") Integer id);

    //Find all messages given account id
    @Query("SELECT message FROM Message message WHERE message.postedBy = :id")
    List<Message> findMessagesByUserId(@Param("id") Integer id);

}
