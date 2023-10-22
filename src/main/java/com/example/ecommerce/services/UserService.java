package com.example.ecommerce.services;

import com.example.ecommerce.exceptions.UserCreationException;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.Discount;
import com.example.ecommerce.models.Item;
import com.example.ecommerce.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final List<User> userList = new ArrayList<>();


    public Optional<List<User>> getUsers(){
        return Optional.of(userList);
    }

    public Optional<User> getUserById(String userID){
        for(User user: userList){
            if(user.getUserId().equals(userID)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public User createUser(String email){
        try{
            String cartID = UtilService.generateRandomUserID();
            List<Item> itemList = new ArrayList<>();
            String userId = Integer.toString(userList.size()+1);
            String discountID = UtilService.generateRandomUserID();
            Discount userDiscount = new Discount(discountID,0,cartID);
            Cart userCart = new Cart(cartID,itemList,userId,userDiscount);
            User newUser = new User(userId,userCart,email);
            userList.add(newUser);
            return newUser;
        }catch(Exception e){
            throw new UserCreationException("Error creating user.." + e.getMessage());
        }


    }
}
