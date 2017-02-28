//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example;

import com.example.MyOAuth2RequestAuthenticator;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

class MyOAuth2RestTemplate extends OAuth2RestTemplate {
    public MyOAuth2RestTemplate(OAuth2ProtectedResourceDetails resource) {
        this(resource, new DefaultOAuth2ClientContext());
    }

    public MyOAuth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
        super(resource, context);
        MyOAuth2RequestAuthenticator myOAuth2RequestAuthenticator = new MyOAuth2RequestAuthenticator();
        this.setAuthenticator(myOAuth2RequestAuthenticator);
    }
}
