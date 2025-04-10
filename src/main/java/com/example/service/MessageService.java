package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private AccountRepository accountRepo;

    public Optional<Message> createMessage(Message msg) {
        if (msg.getMessageText() == null || msg.getMessageText().isBlank() || msg.getMessageText().length() > 255) return Optional.empty();
        if (!accountRepo.existsById(msg.getPostedBy())) return Optional.empty();

        return Optional.of(messageRepo.save(msg));
    }

    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    public Optional<Message> getMessageById(Integer id) {
        return messageRepo.findById(id);
    }

    public Optional<Integer> deleteMessage(Integer id) {
        if (messageRepo.existsById(id)) {
            messageRepo.deleteById(id);
            return Optional.of(1);
        }
        return Optional.empty();
    }

    public Optional<Integer> updateMessage(Integer id, String newText) {
        Optional<Message> existing = messageRepo.findById(id);
        if (existing.isPresent() && newText != null && !newText.isBlank() && newText.length() <= 255) {
            Message msg = existing.get();
            msg.setMessageText(newText);
            messageRepo.save(msg);
            return Optional.of(1);
        }
        return Optional.empty();
    }

    public List<Message> getMessagesByAccountId(Integer accountId) {
        return messageRepo.findByPostedBy(accountId);
    }
}
