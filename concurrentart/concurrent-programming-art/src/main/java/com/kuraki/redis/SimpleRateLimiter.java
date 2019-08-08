package com.kuraki.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;

public class SimpleRateLimiter {

    private Jedis jedis;

    public SimpleRateLimiter(Jedis jedis) {
        this.jedis = jedis;
    }

    public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) throws IOException {
        String key = String.format("hist：%s:%s", userId, actionKey);

        long nowTs = System.currentTimeMillis();
        // 使用pipeline显著提升redis存储效率
        Pipeline pipe = jedis.pipelined();
        pipe.multi();
        // 记录行为
        pipe.zadd(key, nowTs, "" + nowTs);
        // 移除时间窗口之前的行为记录，剩下的都是时间窗口内的
        pipe.zremrangeByScore(key, 0, nowTs - period * 1000);
        // 获取窗口内的行为数量
        Response<Long> count = pipe.zcard(key);
        // 过期时间应该等于时间窗口的长度，再多宽限1秒
        pipe.expire(key, period + 1);
        // 批量执行
        pipe.exec();
        pipe.close();
        // 比较数量是否超标
        return count.get() <= maxCount;
    }

    public static void main(String[] args) throws IOException {
        Jedis jedis = new Jedis();
        SimpleRateLimiter limiter = new SimpleRateLimiter(jedis);

        for (int i = 0; i < 20; i++) {
            System.out.println(limiter.isActionAllowed("kuraki", "reply", 60, 5));
        }
    }
}
