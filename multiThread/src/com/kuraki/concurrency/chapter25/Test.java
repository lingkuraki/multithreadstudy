package com.kuraki.concurrency.chapter25;

public class Test {

    public static void main(String[] args) {
        LRUCache<String, Reference> cache = new LRUCache<>(5, key -> new Reference());
        cache.get("Alex");
        cache.get("Jack");
        cache.get("Gavin");
        cache.get("Dillion");
        cache.get("Led");
        // 上面的数据在数据缓存中的新旧程度为Leo > Dillion > Gavin > Jack > Alex
        cache.get("Jenny");
        System.out.println(cache.toString());
    }
}
