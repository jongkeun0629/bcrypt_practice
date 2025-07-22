package org.example;

import org.example.model.User;
import org.example.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserService();

        User user = service.register("test", "test");
        System.out.println(user.getUsername());
        System.out.println(user.getPasswordHash());
    }
}