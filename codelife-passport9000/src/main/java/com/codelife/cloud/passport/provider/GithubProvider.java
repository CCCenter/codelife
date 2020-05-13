package com.codelife.cloud.passport.provider;

import com.alibaba.fastjson.JSON;
import com.codelife.cloud.util.HttpclientUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GithubProvider {

    public Map<String, Object> getAccessToken(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", "46a91cd8e9d8a254d9f5");
        map.put("client_secret", "269f61e4db9280adbb2070bd21b632b7dc37af8d");
        map.put("code", code);
        map.put("redirect_uri", "http://passport.codelife.com:9000/passport/callback");

        String doPost = HttpclientUtil.doPost("https://github.com/login/oauth/access_token", map, null);
        Map<String, Object> map1 = new HashMap<>();
        String[] split = doPost.split("&");
        for (String s : split) {
            String[] split1 = s.split("=");
            map1.put(split1[0],split1[1]);
        }
        return map1;
    }
    //解析回来的user

    public Map<String, Object> getUserInfo(String accessToken) {
        String doGet = HttpclientUtil.doGet("https://api.github.com/user?access_token=" + accessToken, null, null);
        Map<String, Object> map = JSON.parseObject(doGet, Map.class);
        return map;
    }
}
