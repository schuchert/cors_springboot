package com.shoe.hello;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static java.util.Arrays.asList;

public class TestApplicationContext {
    static ConfigurableApplicationContext context = SpringApplication.start(0);
    static String PORT = context.getEnvironment().getProperty("local.server.port");

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static String urlFor(String url) {
        return String.format("http://localhost:%s/%s", PORT, url);
    }

    public static <T> HttpEntity<T> authorizedEntity(Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "totally authorized");

        return new HttpEntity("parameters", headers);
    }

    public static ResponseEntity<String> restCall(String relativeUrl, HttpMethod httpMethod, Class<String> responseEntityType) {
        String url = TestApplicationContext.urlFor(relativeUrl);
        return new RestTemplate().exchange(url, httpMethod, authorizedEntity(responseEntityType), responseEntityType);
    }
}
