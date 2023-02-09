package com.leetcode.other;

import java.util.HashMap;
import java.util.Map;

/**
 * 1797. 设计一个验证系统
 */
public class AuthenticationManager {

    int timeToLive;
    Map<String, Integer> codeMap;

    public AuthenticationManager(int timeToLive) {
        this.timeToLive = timeToLive;
        this.codeMap = new HashMap<>();
    }

    public void generate(String tokenId, int currentTime) {
        codeMap.put(tokenId, timeToLive + currentTime);
    }

    /**
     * 将给定 tokenId 且 未过期 的验证码在 currentTime 时刻更新。如果给定 tokenId 对应的验证码不存在或已过期，。
     *
     * @param tokenId
     * @param currentTime
     */
    public void renew(String tokenId, int currentTime) {
        if (codeMap.containsKey(tokenId)) {
            // 大于当前时间，说明还没有过期
            if (codeMap.get(tokenId) > currentTime) {
                codeMap.put(tokenId, timeToLive + currentTime);
            }
        }
    }

    public int countUnexpiredTokens(int currentTime) {
        int count = 0;
        for (Map.Entry<String, Integer> entry : codeMap.entrySet()) {
            if (entry.getValue() > currentTime) {
                count++;
            }
        }
        return count;
    }

}
