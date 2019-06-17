package com.shoe.hello;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class TestApplicationContext {
    private ConfigurableApplicationContext context;
    private String PORT;

    TestApplicationContext() {
        this(0);
    }

    TestApplicationContext(int port) {
        context = SpringApplication.start(port);
        PORT = context.getEnvironment().getProperty("local.server.port");
    }

    public <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    String urlFor(String url) {
        return String.format("http://localhost:%s/%s", PORT, url);
    }

    <T> ResponseEntity<T> restCall(String relativeUrl, HttpMethod httpMethod, Class<T> responseEntityType) {
        String url = urlFor(relativeUrl);
        return new RestTemplate().exchange(url, httpMethod, authorizedEntity(), responseEntityType);
    }

    private HttpEntity<?> authorizedEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "totally authorized");

        return new HttpEntity<>("parameters", headers);
    }

}
