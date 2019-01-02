package com.znv.framework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author MaHuiming
 * @date 2018/12/8.
 */
@Service
public class RedisService {
    @Autowired
    RedisTemplate redisTemplate;
}
