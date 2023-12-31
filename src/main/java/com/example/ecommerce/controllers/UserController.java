package com.example.ecommerce.controllers;

import java.util.HashMap;

import com.example.ecommerce.exceptions.ItemNotFoundException;
import com.example.ecommerce.exceptions.UserCreationException;
import com.example.ecommerce.models.*;
import com.example.ecommerce.services.DiscountService;
import com.example.ecommerce.services.ItemService;
import com.example.ecommerce.services.UserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/v1")
public class UserController {

   private final UserService userService;
   private final ItemService itemService;

   private static final Logger logger = LoggerFactory.getLogger(UserController.class);


   @Autowired
   public UserController(UserService userService, ItemService itemService, DiscountService discountService){
      this.userService = userService;
      this.itemService = itemService;

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




}
