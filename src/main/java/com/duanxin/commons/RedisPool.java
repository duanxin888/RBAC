package com.duanxin.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName RedisPool
 * @Description 对Redis的封装
 * @date 2019/7/24 8:25
 */
@Component("redisPool")
@Slf4j
public class RedisPool {

    @Resource(name = "shardedJedisPool")
    private ShardedJedisPool shardedJedisPool;

    /**
     * @description 获取客户端连接
     * @param []
     * @date 2019/7/24 8:29
     * @return redis.clients.jedis.ShardedJedis
     **/
    public ShardedJedis instance() {
        return shardedJedisPool.getResource();
    }

    /**
     * @description 关闭redis资源
     * @param [shardedJedis]
     * @date 2019/7/24 8:55
     **/
    public void safeClose(ShardedJedis shardedJedis) {
        try {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        } catch (Exception e) {
            log.error("return redis resource exception", e);
        }
    }
}
