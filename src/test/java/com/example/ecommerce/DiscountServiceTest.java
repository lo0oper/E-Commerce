package com.example.ecommerce;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.services.DiscountService;
import com.example.ecommerce.services.UtilService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class DiscountServiceTest {
    private DiscountService discountService;
    private Cart cart;

    private UtilService utilService;

    @BeforeEach
    void setUp() {
        discountService = new DiscountService();
        cart = new Cart(
                "1",new ArrayList<>(),"1",new ArrayList<>(), 0
        );
    }

    @Test
    void testCheckDiscountWithZeroTotalOrders() {
        assertFalse(discountService.checkDiscount(cart, 0, 2));
    }

    @Test
    void testCheckDiscountWithNonZeroTotalOrdersAndNoDiscount() {
        assertFalse(discountService.checkDiscount(cart, 5, 3));
    }

    @Test
    void testCheckDiscountWithDiscountEligible() {
        assertTrue(discountService.checkDiscount(cart, 10, 2));
    }

    @Test
    void testNumberOfTimesDiscountEligible() {
        assertEquals(5, discountService.numberOfTimesDiscountEligible(10, 2));
    }

    @Test
    void testGetTotalDiscountedAmount() {
        assertEquals(50, discountService.getTotalDiscountedAmount(100, 5));
    }

    @Test
    void testGetDiscountCode() {
        // Create a mock instance of UtilService
        UtilService utilService = mock(UtilService.class);

        // Set the UtilService instance in your DiscountService
        String discountCode = discountService.getDiscountCode();
        // Check if discountCode is of type String
        assertTrue(discountCode instanceof String);

    }
}
