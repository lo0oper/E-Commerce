package com.example.ecommerce.services;

import com.example.ecommerce.models.Cart;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class DiscountService {
    private int defaultDiscountNumber =2;
    public boolean checkDiscount(Cart userCart, int totalOrders, int discountNumber){
        if(totalOrders%discountNumber==0){
            return true;
        }
        return false;
    }

    public int numberOfTimesDiscountEligible(int totalOrders, int discountNumber ){
        int ans ;
        ans = totalOrders/discountNumber;
        return ans;
    }
    public int getTotalDiscountedAmount(int totalCost, int numberOfTimesDiscountEligible){
        return  numberOfTimesDiscountEligible * (totalCost/10);
    }

    public String getDiscountCode(){
        return UtilService.generateRandomUserID();
    }
}
