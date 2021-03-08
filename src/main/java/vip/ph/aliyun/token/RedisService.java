package vip.ph.aliyun.token;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisService {


    @Resource
    StringRedisTemplate stringRedisTemplate;


    /**
     * 写入缓存时设置有效时间
     *
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean setEx(String key, String value, Long expireTime) {

        boolean result = false;

        try {
            ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
            opsForValue.set(key, value);

            stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }


    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
            opsForValue.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 判断缓存中是否有对应的key
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return stringRedisTemplate.hasKey(key);
    }


    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {

        Object result = null;
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        result = opsForValue.get(key);
        return result;
    }


    /**
     * 删除对应的key
     *
     * @param key
     * @return
     */
    public boolean remove(final String key) {
        if (exists(key)) {
            Boolean delete = stringRedisTemplate.delete(key);
            return delete;
        }

        return false;
    }
}
