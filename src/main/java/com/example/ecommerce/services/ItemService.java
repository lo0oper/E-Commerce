package com.example.ecommerce.services;

import com.example.ecommerce.models.Item;
import com.example.ecommerce.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ItemService {
    private final List<Item> itemList = new ArrayList<>();


    public Item registerItem(int itemCost, String itemType){
        String itemId = Integer.toString(itemList.size()+1);
        Item newItem = new Item(itemId,itemType,itemCost);
        itemList.add(newItem);
        return newItem;
    }


    public List<Item> getItemList(){
        return itemList;
    }

    public Optional<Item> getItemById(String itemId){
        for(Item item: itemList){
            if(item.getItemId().equals(itemId)){
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    public Integer getTotalItems(){
        return itemList.size();
    }

}
