package com.example.ecommerce;

import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.User;
import com.example.ecommerce.services.UserService;
import com.example.ecommerce.services.UtilService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private UtilService utilService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService();
        utilService = new UtilService();
    }

    @Test
    public void testGetUsers() {

        List<User> userList = List.of(new User("1", new Cart(), "user1@example.com", 0));
        Optional<List<User>> result = userService.getUsers();
        assertEquals(new ArrayList<>(), result.orElse(null));
    }

    @Test
    public void NouserExistingTest() {

        List<User> userList = List.of(new User("1", new Cart(), "user1@example.com", 0));
//        userService.setUserList(userList);
        Optional<User> result = userService.getUserById("1");
        assertEquals(null, result.orElse(null));
    }

}
