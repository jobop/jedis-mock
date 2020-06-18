package com.github.microwww.database;

import redis.clients.util.SafeEncoder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataHash extends AbstractValueData<Map<HashKey, byte[]>> {

    private final Map<HashKey, byte[]> origin;

    public DataHash() {
        this.origin = new ConcurrentHashMap<>();
        this.data = Collections.unmodifiableMap(origin);
    }

    //HDEL
    public synchronized byte[] remove(HashKey key) {
        return origin.remove(key);
    }

    //HEXISTS
    //HGET
    //HGETALL

    //HINCRBY
    public synchronized byte[] incrBy(HashKey key, long inc) {
        byte[] bytes = origin.get(key);
        String s = new BigInteger(SafeEncoder.encode(bytes)).add(BigInteger.valueOf(inc)).toString();
        byte[] encode = SafeEncoder.encode(s);
        origin.put(key, encode);
        return encode;
    }

    //HINCRBYFLOAT
    public synchronized byte[] incrByFloat(HashKey key, double inc) {
        byte[] bytes = origin.get(key);
        String s = new BigDecimal(SafeEncoder.encode(bytes)).add(BigDecimal.valueOf(inc)).toPlainString();
        byte[] encode = SafeEncoder.encode(s);
        origin.put(key, encode);
        return encode;
    }

    //HKEYS
    //HLEN
    //HMGET
    //HMSET
    //HSET
    public synchronized void put(HashKey key, byte[] bytes) {
        origin.put(key, bytes);
    }

    //HSETNX
    public synchronized void putIfAbsent(HashKey key, byte[] bytes) {
        origin.putIfAbsent(key, bytes);
    }
    //HVALS
    //HSCAN
}
