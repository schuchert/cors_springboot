package com.shoe.hello;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloWorldController {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/greeting", method = { RequestMethod.GET, RequestMethod.POST })
    public HelloWorldResponse greetingGet(String name) {
        return new HelloWorldResponse(counter.incrementAndGet(), "Hello, World!");
    }
}
