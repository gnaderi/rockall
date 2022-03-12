package com.rockall.trade.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class RestApiHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiHelper.class);
    private final RestTemplate restTemplate;

    @Autowired
    public RestApiHelper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T executeServiceEndpoint(String url, Class<T> clazz) {
        return executeServiceEndpoint(url, clazz, HttpMethod.GET);
    }

    public <T> T executeServiceEndpoint(String url, Class<T> clazz,
                                        HttpMethod httpMethod) {
        return executeServiceEndpoint(url, clazz, httpMethod, getHttpEntity());
    }

    public <T> T executeServiceEndpoint(String endPointUrl, Class<T> clazz, HttpMethod httpMethod,
                                        HttpEntity<?> entity) {
        LOGGER.debug("Calling resource endpoint operation [{}] ....", endPointUrl);
        return restTemplate.exchange(
                endPointUrl,
                httpMethod,
                entity,
                clazz).getBody();
    }


    private HttpEntity<?> getHttpEntity() {
        return new HttpEntity<>(createHttpHeaders());
    }

    private HttpEntity<?> getHttpEntity(String user, String password) {
        return new HttpEntity<>(createHttpHeaders(user, password));
    }

    private HttpHeaders createHttpHeaders(String user, String password) {
        HttpHeaders headers = createHttpHeaders();
        headers.setBasicAuth(user, password);
        return headers;
    }

    private HttpHeaders createHttpHeaders() {
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        return headers;
    }

}
