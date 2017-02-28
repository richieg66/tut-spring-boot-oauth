package com.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * Created by grimshr1 on 27/02/2017.
 */
@Configuration
public class Config {


    @Autowired
    private ResourceServerProperties sso;

    @Bean
    public ResourceServerTokenServices myUserInfoTokenServices() {
        return new MyUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
    }

}


//class MyUserInfoTokenServices implements ResourceServerTokenServices {
//    protected final Log logger = LogFactory.getLog(this.getClass());
//    private final String userInfoEndpointUrl;
//    private final String clientId;
//    private OAuth2RestOperations restTemplate;
//    private String tokenType = "Bearer";
//    private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();
//    private PrincipalExtractor principalExtractor = new MyFixedPrincipalExtractor();
//
//    public MyUserInfoTokenServices(String userInfoEndpointUrl, String clientId) {
//        this.userInfoEndpointUrl = userInfoEndpointUrl;
//        this.clientId = clientId;
//    }
//
//    public void setTokenType(String tokenType) {
//        this.tokenType = tokenType;
//    }
//
//    public void setRestTemplate(OAuth2RestOperations restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public void setAuthoritiesExtractor(AuthoritiesExtractor authoritiesExtractor) {
//        Assert.notNull(authoritiesExtractor, "AuthoritiesExtractor must not be null");
//        this.authoritiesExtractor = authoritiesExtractor;
//    }
//
//    public void setPrincipalExtractor(PrincipalExtractor principalExtractor) {
//        Assert.notNull(principalExtractor, "PrincipalExtractor must not be null");
//        this.principalExtractor = principalExtractor;
//    }
//
//    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
//        Map map = this.getMap(this.userInfoEndpointUrl, accessToken);
//        if(map.containsKey("error")) {
//            this.logger.debug("userinfo returned error: " + map.get("error"));
//            throw new InvalidTokenException(accessToken);
//        } else {
//            return this.extractAuthentication(map);
//        }
//    }
//
//    private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
//
//        map.forEach((s, o) -> System.out.println("key is " + s + " value is " + o));
//
//        Object principal = this.getPrincipal(map);
//        List authorities = this.authoritiesExtractor.extractAuthorities(map);
//        OAuth2Request request = new OAuth2Request((Map)null, this.clientId, (Collection)null, true, (Set)null, (Set)null, (String)null, (Set)null, (Map)null);
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
//        token.setDetails(map);
//        return new OAuth2Authentication(request, token);
//    }
//
//
//    protected Object getPrincipal(Map<String, Object> map) {
//        Object principal = this.principalExtractor.extractPrincipal(map);
//        return principal == null?"unknown":principal;
//    }
//
//    public OAuth2AccessToken readAccessToken(String accessToken) {
//        throw new UnsupportedOperationException("Not supported: read access token");
//    }
//
//    private Map<String, Object> getMap(String path, String accessToken) {
//        this.logger.info("Getting user info from: " + path);
//
//        try {
//            Object ex = this.restTemplate;
//            if(ex == null) {
//                BaseOAuth2ProtectedResourceDetails existingToken = new BaseOAuth2ProtectedResourceDetails();
//                existingToken.setClientId(this.clientId);
//                ex = new OAuth2RestTemplate(existingToken);
//            }
//
//            OAuth2AccessToken existingToken1 = ((OAuth2RestOperations)ex).getOAuth2ClientContext().getAccessToken();
//            if(existingToken1 == null || !accessToken.equals(existingToken1.getValue())) {
//                DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
//                token.setTokenType(this.tokenType);
//                ((OAuth2RestOperations)ex).getOAuth2ClientContext().setAccessToken(token);
//            }
//
////            return (Map)((OAuth2RestOperations)ex).getForEntity(path, Map.class, new Object[0]).getBody();
////            final String test = "test";
//            return (Map)((OAuth2RestOperations)ex).postForEntity(path, null, Map.class, new Object[0]).getBody();
//        } catch (Exception var6) {
//            this.logger.info("Could not fetch user details: " + var6.getClass() + ", " + var6.getMessage());
//            return Collections.singletonMap("error", "Could not fetch user details");
//        }
//    }

//}
