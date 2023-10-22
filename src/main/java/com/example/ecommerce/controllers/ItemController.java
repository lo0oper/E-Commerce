package com.example.ecommerce.controllers;

import com.example.ecommerce.models.Item;
import com.example.ecommerce.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ItemController {

    private final ItemService itemService;
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }


    @PostMapping("/item")
    public ResponseEntity<Object> createItem(@RequestBody Map<String,String>req) {
        String itemType = req.get("itemType");
        int cost = Integer.parseInt(req.get("cost"));
        Item newItem = itemService.registerItem(cost,itemType);
        logger.info("create item endpoint invoked with Cost: " + newItem.getCost() +", ItemType: "+ newItem.getItemType());
        return ResponseEntity.status(HttpStatus.OK).body(newItem);
    }


    @GetMapping("/item")
    public ResponseEntity<Object> getItem(@RequestParam(required = false) String itemId){

        if(itemId != null){
            logger.info("get item endpoint invoked with itemID: " + itemId);
            Optional<Item> item = itemService.getItemById(itemId);
            if(item.isPresent()){
                return ResponseEntity.status(HttpStatus.OK).body(item);
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
            }
        }else{
            logger.info("get item endpoint invoked to get all items");
            List<Item> itemList = itemService.getItemList();
            return ResponseEntity.status(HttpStatus.OK).body(itemList);
        }

    }
}
