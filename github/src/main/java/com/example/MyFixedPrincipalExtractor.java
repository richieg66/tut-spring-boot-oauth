package com.example;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;

import java.util.Map;

/**
 * Created by grimshr1 on 27/02/2017.
 */
public class MyFixedPrincipalExtractor implements PrincipalExtractor {
    private static final String[] PRINCIPAL_KEYS = new String[]{"user", "username", "user_name", "userid", "user_id", "login", "id", "name"};

    public MyFixedPrincipalExtractor() {
    }

    public Object extractPrincipal(Map<String, Object> map) {
        String[] var2 = PRINCIPAL_KEYS;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String key = var2[var4];
            if(map.containsKey(key)) {
                return map.get(key);
            }
        }

        return null;
    }
}
