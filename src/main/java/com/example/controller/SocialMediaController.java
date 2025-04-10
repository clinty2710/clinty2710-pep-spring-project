package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
 public class SocialMediaController {
 
     @Autowired
     private AccountService accountService;
 
     @Autowired
     private MessageService messageService;
    
     // user registration (POST /register)
     @PostMapping("/register")
     public ResponseEntity<?> register(@RequestBody Account account) {
         Optional<Account> result = accountService.register(account);
         if (result.isEmpty()) {
             return ResponseEntity.badRequest().build();
         }
         if (result.get().getAccountId() == -1) {
             return ResponseEntity.status(409).build();
         }
         return ResponseEntity.ok(result.get());
     }
     
     //user login (POST /login)
     @PostMapping("/login")
     public ResponseEntity<?> login(@RequestBody Account account) {
         return accountService.login(account)
                 .map(ResponseEntity::ok)
                 .orElse(ResponseEntity.status(401).build());
     }
 
    // Create new message (POST /messages)
     @PostMapping("/messages")
     public ResponseEntity<?> postMessage(@RequestBody Message message) {
         return messageService.createMessage(message)
                 .map(ResponseEntity::ok)
                 .orElse(ResponseEntity.badRequest().build());
     }
 
    // Get messages (GET /messages)
     @GetMapping("/messages")
     public ResponseEntity<List<Message>> getAllMessages() {
         return ResponseEntity.ok(messageService.getAllMessages());
     }
 
    // Get message from ID (GET /messages/{id})
     @GetMapping("/messages/{id}")
     public ResponseEntity<Message> getMessageById(@PathVariable Integer id) {
         return messageService.getMessageById(id)
                 .map(ResponseEntity::ok)
                 .orElse(ResponseEntity.ok(null));
     }
 
    // Delete message by ID
     @DeleteMapping("/messages/{id}")
     public ResponseEntity<?> deleteMessage(@PathVariable Integer id) {
         Optional<Integer> result = messageService.deleteMessage(id);
         if (result.isPresent()) {
             return ResponseEntity.ok(result.get());
         } else {
             return ResponseEntity.ok().build(); 
         }
     }
 
    // Update message text 
     @PatchMapping("/messages/{id}")
     public ResponseEntity<?> updateMessage(@PathVariable Integer id, @RequestBody Message newMsg) {
         return messageService.updateMessage(id, newMsg.getMessageText())
                 .map(ResponseEntity::ok)
                 .orElse(ResponseEntity.badRequest().build());
     }
 
    // Get messages by ID
     @GetMapping("/accounts/{accountId}/messages")
     public ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable Integer accountId) {
         return ResponseEntity.ok(messageService.getMessagesByAccountId(accountId));
     }
 }