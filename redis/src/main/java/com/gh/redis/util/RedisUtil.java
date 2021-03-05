package com.gh.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @program: blog-cloud
 * @description redis缓存工具类
 * @author: 3hgh
 * @create: 2021-03-05 10:26
 **/
@Component
public class RedisUtil {

    @Autowired
    RedisTemplate<String, Serializable> redisTemplate;   // key-value是对象的

    public RedisUtil(){

    }

    /**
     * 判断是否存在key
     * @param key 主键
     * @return true或false
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 新增、修改Redis键值
     * @param key 主键
     * @param value 值
     */
    public void insertOrUpdate(String key, Serializable value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 新增、修改Redis键值,并设置有效时间（秒）
     * @param key 主键
     * @param value 值
     * @param seconds 有效时间（秒）
     */
    public void insertOrUpdateBySeconds(String key, Serializable value, long seconds) {
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    /**
     * 新增、修改Redis键值,并设置有效时间（分）
     * @param key 主键
     * @param value 值
     * @param minutes 有效时间（分）
     */
    public void insertOrUpdateByMinutes(String key, Serializable value, long minutes) {
        redisTemplate.opsForValue().set(key, value, minutes, TimeUnit.MINUTES);
    }

    /**
     * 新增、修改Redis键值,并设置有效时间（小时）
     * @param key 主键
     * @param value 值
     * @param hours 有效时间（小时）
     */
    public void insertOrUpdateByHours(String key, Serializable value, long hours) {
        this.redisTemplate.opsForValue().set(key, value, hours, TimeUnit.HOURS);
    }

    /**
     * 新增、修改Redis键值,并设置有效时间（天）
     * @param key 主键
     * @param value 值
     * @param days 有效时间（天）
     */
    public void insertOrUpdateByDays(String key, Serializable value, long days) {
        this.redisTemplate.opsForValue().set(key, value, days, TimeUnit.DAYS);
    }

    /**
     * 通过主键获取值
     * @param key 主键
     * @return
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取redis的所有key里包含pattern字符的key集
     * @param pattern 模糊查询字符
     * @return
     */
    public Set<String> getPattern(String pattern) {
        return redisTemplate.keys("*" + pattern + "*");
    }

    /**
     * 删除指定redis缓存
     * @param key 主键
     * @return
     */
    public boolean remove(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 删除指定的多个缓存
     * @param keys 主键1，主键2，...
     * @return 删除主键数
     */
    public int removes(String... keys){
        int count = 0;
        List<String> deleteFails = new ArrayList<>();

        for (String key : keys) {
            if (Boolean.TRUE.equals(redisTemplate.delete(key))) {
                ++count;
            } else {
                deleteFails.add(key);
            }
        }

        if (!CollectionUtils.isEmpty(deleteFails)) {
            System.err.println("======> Redis缓存删除失败的key：" + deleteFails.toString());
        }
        return count;
    }

    /**
     * 删除所有的键值对数据
     * @return 清除键值对数据量
     */
    public int removeAll(){
        Set<String> keys = redisTemplate.keys("*");
        Long delete = 0L;

        if (keys != null) {
            delete = redisTemplate.delete(keys);
        }

        return delete != null ? delete.intValue() : 0;
    }

}