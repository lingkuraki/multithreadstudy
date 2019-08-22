package com.kuraki.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

public class TransactionDemo {

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        String userId = "kuraki";
        String key = keyFor(userId);
        jedis.setnx(key, String.valueOf(5));
        System.out.println(doubleAccount(jedis, userId));
        jedis.close();
    }

    private static int doubleAccount(Jedis jedis, String userId) {

        String key = keyFor(userId);
        while (true) {
            jedis.watch(key);
            int value = Integer.parseInt(jedis.get(key));
            value *= 2;
            Transaction transaction = jedis.multi();
            transaction.set(key, String.valueOf(value));
            List<Object> res = transaction.exec();
            if (res != null) {
                break;
            }
        }
        return Integer.parseInt(jedis.get(key));
    }

    private static String keyFor(String userId) {
        return "account_" + userId;
    }
}
