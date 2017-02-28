package com.example;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by grimshr1 on 21/02/2017.
 */
@Configuration
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class WorkaroundRestTemplateCustomizer implements UserInfoRestTemplateCustomizer {


    @Override
    public void customize(OAuth2RestTemplate template) {
        template.setErrorHandler(new ResponseErrorHandlerImpl());
        template.setInterceptors(new ArrayList<>(template.getInterceptors()));
    }

    @Configuration
    public static  class ResponseErrorHandlerImpl implements ResponseErrorHandler {


        @Override
        public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
            System.out.println("here!");
        }

        @Override
        public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
            return false;
        }
    }
}

