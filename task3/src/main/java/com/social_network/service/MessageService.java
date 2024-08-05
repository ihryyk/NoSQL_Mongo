package com.social_network.service;

import com.social_network.model.dao.MessageDao;
import com.social_network.model.dao.UserDao;
import com.social_network.model.document.Message;

import java.time.DayOfWeek;

public class MessageService {

    private final MessageDao messageDao;
    private final UserDao userDao;

    public MessageService() {
        messageDao = new MessageDao();
        userDao = new UserDao();
    }

    public Message saveMessage(Message message) {
        if (userDao.getUserById(message.getSender()).isEmpty()) {
            throw new IllegalArgumentException("Sender with id " + message.getSender() + " does not exist");
        }
        if (userDao.getUserById(message.getReceiver()).isEmpty()) {
            throw new IllegalArgumentException("Receiver with id " + message.getReceiver() + " does not exist");
        }
        return messageDao.save(message);
    }

    public Double getAverageNumberOfMessagesOnDayOfWeek(DayOfWeek dayOfWeek) {
        return messageDao.getAverageNumberOfMessagesOnDayOfWeek(dayOfWeek);
    }

}
