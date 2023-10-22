package com.example.ecommerce.controllers;


import com.example.ecommerce.exceptions.UserCreationException;
import com.example.ecommerce.models.Item;
import com.example.ecommerce.models.User;
import com.example.ecommerce.services.UserService;
import com.example.ecommerce.services.UtilService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.expression.spel.ast.Literal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class UserController {

   private final UserService userService;
   private static final Logger logger = LoggerFactory.getLogger(UserController.class);


   @Autowired
   public UserController(UserService userService){
      this.userService = userService;

   }

   @GetMapping("/healthcheck")
   public String healthCheck(){
      return "Application is online";
   }

   @GetMapping("/user")
   public ResponseEntity<?> getUsers(@RequestParam(required = false) String userId){
      if(userId != null){
         logger.info("/user endpoint invoked with =>"+userId);
         Optional<User> user = userService.getUserById(userId);
         if (user.isPresent()){
             return ResponseEntity.ok(user);
         } else {
             return ResponseEntity.ok(new ArrayList<>());
         }
      }else{
         logger.info("/user endpoint invoked without userId");
         Optional<List<User>> usersList = userService.getUsers();
         if(usersList.isPresent()){
            return ResponseEntity.ok(usersList);
         } else {
            return  ResponseEntity.ok(new ArrayList<>());
         }
      }
   }


   @PostMapping("/user")
   public ResponseEntity<Object> createUser(@RequestBody Map<String, String> request) {
      try {
         logger.info("Post /user endpoint invoked with "+ request);
         String email = request.get("email");
         User newUser = userService.createUser(email);
         return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
      } catch (UserCreationException ex) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
      }
   }


   @PostMapping("/item")
   public ResponseEntity<Object> createItem(@RequestBody Item item){
      logger.info("create item endpoint invoked with Cost: " + item.getCost() +", ItemType: "+ item.getItemType());

      return ResponseEntity.status(HttpStatus.OK).body("Item not created yet");
   }

}
