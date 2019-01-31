package com.shoe.hello;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static final int SERVER_PORT = 9000;
    public static final int CLIENT_PORT = 8080;

    public static void main(String[] args) {
        SpringApplication.start(CLIENT_PORT, args);
        SpringApplication.start(SERVER_PORT, args);
    }
}
