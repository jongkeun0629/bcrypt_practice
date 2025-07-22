package org.example;

import org.example.model.User;
import org.example.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserService();

        service.register("test", "test");

        service.login("test1", "test1");
    }
}