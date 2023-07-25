package com.grupob.ServiMarket.service;

import com.grupob.ServiMarket.entity.Message;
import com.grupob.ServiMarket.entity.Publication;
import com.grupob.ServiMarket.repository.MessageRepository;
import com.grupob.ServiMarket.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    //------------------------CREATE--------------------------
    @Transactional
    public void create (Message message){
        messageRepository.save(message);
    }

    //------------------------READ--------------------------

    public List<Message> list(){
        List<Message> message = new ArrayList<>();
        message=messageRepository.findAll();
        return message;
    }


    //---------------------GET PUBLICATION BY ID-----------------
    public Message getMessageById(Long id){
        return messageRepository.findById(id).orElse(null);
    }
    //------------------------UPDATE--------------------------
    public Message updateMessage (Message message){
        Message updateMessage = messageRepository.findById(message.getId()).orElse(null);
        if(updateMessage!=null){
            updateMessage.setMensaje(message.getMensaje());
            updateMessage.setProvider(message.getProvider());
            updateMessage.setUserClient(message.getUserClient());

            messageRepository.save(updateMessage);
            return updateMessage;
        }
        return null;
    }

    //------------------------DELETE--------------------------

    @Transactional
    public void delete (Long id){
        Optional<Message> response = messageRepository.findById(id);
        if(response!= null){
            Message messageToDelete = response.get();
            messageRepository.delete(messageToDelete);
        }
    }
}
