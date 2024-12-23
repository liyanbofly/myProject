package com.self.web.util;

public class RedisTemplate <K, V>{
    public void set(K key, V value) {
        System.out.println("RedisTemplate set key: " + key + " value: " + value);
    }

    public V get(K key) {
        System.out.println("RedisTemplate get key: " + key);
        return null;
    }
}
