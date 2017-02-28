//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example;

import java.io.IOException;
import java.io.OutputStream;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RequestAuthenticator;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.StringUtils;

class MyOAuth2RequestAuthenticator implements OAuth2RequestAuthenticator {
    public MyOAuth2RequestAuthenticator() {
    }

    public void authenticate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext clientContext, ClientHttpRequest request) {
        OAuth2AccessToken accessToken = clientContext.getAccessToken();
        if(accessToken == null) {
            throw new AccessTokenRequiredException(resource);
        } else {
            String tokenType = accessToken.getTokenType();
            if(!StringUtils.hasText(tokenType)) {
                tokenType = "Bearer";
            }

            request.getHeaders().set("Authorization", String.format("%s %s", new Object[]{tokenType, accessToken.getValue()}));

            try {
                OutputStream e = request.getBody();
                String body = "token=" + accessToken.getValue();
                e.write(body.getBytes());
                e.flush();
            } catch (IOException var8) {
                var8.printStackTrace();
            }

        }
    }
}
