package com.duanxin.service;

import com.duanxin.beans.CacheKeyConstants;
import com.duanxin.commons.RedisPool;
import com.duanxin.utils.JsonMapper;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SysCacheService
 * @Description Service层缓存机制类
 * @date 2019/7/24 8:55
 */
@Service
@Slf4j
public class SysCacheService {

    @Resource(name = "redisPool")
    private RedisPool redisPool;

    /**
     * @description 缓存存储
     * @param [toSavedValue, timeoutSeconds, prefix]
     * @date 2019/7/24 9:00
     **/
    public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyConstants prefix) {
        saveCache(toSavedValue, timeoutSeconds, prefix, null);
    }

    /**
     * @description 缓存存储重载方法，适配jedisAPI
     * @param [toSavedValue, timeoutSeconds, prefix, keys]
     * @date 2019/7/24 9:02
     **/
    public void saveCache(String toSavedValue, int timeoutSeconds,
                          CacheKeyConstants prefix, String... keys) {
        if (toSavedValue == null) {
            return ;
        }
        ShardedJedis shardedJedis = null;
        try {
            // 转化缓存key值
            String cacheKey = generateCacheKey(prefix, keys);
            shardedJedis = redisPool.instance();
            shardedJedis.setex(cacheKey, timeoutSeconds, toSavedValue);
        } catch (Exception e) {
            log.error("save cache exception, prefix:{}, keys:{}",
                    prefix.name(), JsonMapper.obj2String(keys), e);
        } finally {
            // 关闭资源
            redisPool.safeClose(shardedJedis);
        }
    }

    /**
     * @description 从缓存中取值
     * @param [prefix, keys]
     * @date 2019/7/24 9:24
     * @return java.lang.String
     **/
    public String getFromCache(CacheKeyConstants prefix, String... keys) {
        ShardedJedis shardedJedis = null;
        try {
            String cacheKey = generateCacheKey(prefix, keys);
            shardedJedis = redisPool.instance();
            String value = shardedJedis.get(cacheKey);
            return value;
        } catch (Exception e) {
            log.error("get from cache exception, prefix:{}, keys:{}",
                    prefix.name(), JsonMapper.obj2String(keys), e);
        } finally {
            // close resource
            redisPool.safeClose(shardedJedis);
        }
        return null;
    }

    /**
     * @description 对key值进行转化
     * @param [prefix, keys]
     * @date 2019/7/24 9:14
     * @return java.lang.String
     **/
    private String generateCacheKey(CacheKeyConstants prefix, String... keys) {
        String key = prefix.name();
        if (keys != null && keys.length > 0) {
            key += "_" + Joiner.on("_").join(keys);
        }
        return key;
    }
}
