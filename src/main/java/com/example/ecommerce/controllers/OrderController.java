package com.example.ecommerce.controllers;


import com.example.ecommerce.exceptions.ItemNotFoundException;
import com.example.ecommerce.models.*;
import com.example.ecommerce.services.DiscountService;
import com.example.ecommerce.services.ItemService;
import com.example.ecommerce.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final ItemService itemService;
    private final UserService userService;
    private final DiscountService discountService;

    @Autowired
    public OrderController(ItemService itemService, UserService userService, DiscountService discountService) {
        this.itemService = itemService;
        this.userService = userService;
        this.discountService = discountService;
    }


    @PostMapping("/user/order")
    public ResponseEntity<Object> orderItems(@RequestBody OrderRequest req) throws ItemNotFoundException {
        logger.info(req.toString());
        String userId = req.getUserId();
        Optional<User> user = userService.getUserById(userId);
        if(user.isPresent()){
            int totalOrders = user.get().getTotalOrders();
            user.get().setTotalOrders(totalOrders+1);
            List<OrderItem> orders = req.getOrderItems();
            for(OrderItem order: orders){
                String itemId = order.getItemId();
                int quantity = order.getQuantity();
                Cart userCart = user.get().getUserCart();
                List<Item> itemList = userCart.getItemList();
                int cost = 0;
                for(int i =0; i<quantity; i++) {
                    Optional<Item> newItem = itemService.getItemById(itemId);
                    if (newItem.isPresent()) {
                        itemList.add(newItem.get());
                        cost = cost + newItem.get().getCost();
                    } else {
                        logger.error("Item with id: " + itemId + " can not be found");
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item: "+itemId+ " not found");
//                  throw new ItemNotFoundException("Item with id: " + itemId + " can not be found");
                    }
                }
                int totalCost = userCart.getTotalCost();
                userCart.setTotalCost(totalCost+cost);
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
        Map<String,String> response = new HashMap<>();
        response.put("message","Order placed");
        response.put("OrderDetails",req.toString());
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @PostMapping("/user/discount")
    public ResponseEntity<Object> checkout(@RequestBody Map<String,String> req){
        String userId = req.get("userId");
        int discountNumber = Integer.parseInt(req.get("discountNumber"));
        Optional<User> user = userService.getUserById(userId);
        int totalOrders = user.get().getTotalOrders();
        Cart userCart = user.get().getUserCart();
        String discountMessage ="";
        int totalDiscountAmount=0;
        List<String> discountCodes = new ArrayList<>();
        if(discountService.checkDiscount(userCart,totalOrders,discountNumber )){
            int numberOfTimesDiscountEligible = discountService.numberOfTimesDiscountEligible(user.get().getTotalOrders(),discountNumber);
            totalDiscountAmount = discountService.getTotalDiscountedAmount(userCart.getTotalCost(),numberOfTimesDiscountEligible);
            discountMessage = "You are eligible for discount of "+ Integer.toString(totalDiscountAmount);
            for(int i =0; i<numberOfTimesDiscountEligible; i++){
                String discountCode = discountService.getDiscountCode();
                List<Discount> discountList = userCart.getDiscounts();
                Discount newDiscount = new Discount(Integer.toString(userCart.getDiscounts().size()+1),totalDiscountAmount, userCart.getCartId(),userId,discountCode);
                discountList.add(newDiscount);
                discountCodes.add(discountCode);
            }
        }else{
            return ResponseEntity.status(HttpStatus.OK).body("No discount is eligible for you currently. Try buying more stuff");
        }
        Map<String,String> response = new HashMap<>();
        response.put("message",discountMessage);
        response.put("totalOrders",Integer.toString(totalOrders));
        response.put("totalPurchaseAmount",Integer.toString(userCart.getTotalCost()));
        response.put("totalDiscountAmount",Integer.toString(totalDiscountAmount));
        String strDiscountCodes ="";
        for(int i =0; i<discountCodes.size(); i++){
            strDiscountCodes = strDiscountCodes + discountCodes.get(i);
        }
        response.put("totalDiscountCodes",strDiscountCodes);

        return ResponseEntity.status(HttpStatus.OK).body(response);



    }

}
