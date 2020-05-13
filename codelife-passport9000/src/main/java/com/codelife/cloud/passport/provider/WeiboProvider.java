package com.codelife.cloud.passport.provider;

import com.alibaba.fastjson.JSON;
import com.codelife.cloud.util.HttpclientUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WeiboProvider {

    public Map<String, Object> getUserInfo(String accessToken, Long uid) {
        String s1 = "https://api.weibo.com/2/users/show.json?access_token=" + accessToken + "&uid=" + uid;
        String s = HttpclientUtil.doGet(s1, null, null);
        Map<String, Object> map = JSON.parseObject(s, Map.class);
        return map;
    }

    public Map<String, Object> getAccessToken(String code) {
        String s = "https://api.weibo.com/oauth2/access_token";

        Map<String, String> map = new HashMap<>();
        map.put("client_id", "1386857352");
        map.put("client_secret", "dedc9a79efd4a5f75a7399a54fea4960");
        map.put("grant_type", "authorization_code");
        map.put("code", code);
        map.put("redirect_uri", "http://passport.codelife.com:9000/passport/vlogin");

        String doPost = HttpclientUtil.doPost(s, map, null);
        Map<String, Object> map1 = JSON.parseObject(doPost, Map.class);

        return map1;
    }

}
