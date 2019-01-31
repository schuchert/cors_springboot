package com.shoe.hello;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;

public class SpringApplication {
    public static ConfigurableApplicationContext start(int port) {
        return start(port, new String[]{});
    }

    public static ConfigurableApplicationContext start(int port, String[] args) {
        HashMap<String, Object> props = new HashMap<>();
        props.put("server.port", port);

        return new SpringApplicationBuilder()
                .sources(Main.class)
                .properties(props)
                .run(args);
    }
}
