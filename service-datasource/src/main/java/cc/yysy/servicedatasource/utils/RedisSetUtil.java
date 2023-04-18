package cc.yysy.servicedatasource.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis操作工具类
 * 对于Redis中Set类型的操作
 */
@Component
public class RedisSetUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    //默认缓存时间60s
    public final long DEFAULT_TIME = 72000;

    /**
     * 向Set中添加元素
     * @param key
     * @param value
     * @return
     */
    private boolean add(String key, Object value) {
        boolean result = false;
        try {
            redisTemplate.opsForSet().add(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 向Set中添加元素
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean add(String key, Object value ,long time){
        try {
            if(time>0){
                redisTemplate.opsForSet().add(key, value, time, TimeUnit.SECONDS);
            }else{
                add(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 向Set中移除元素
     * @param key
     * @param value
     * @return
     */
    public boolean remove(String key, Object value) {
        boolean result = false;
        try {
            redisTemplate.opsForSet().remove(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取集合的大小
     * @param key
     */
    public int size(String key){
        try {
            return redisTemplate.opsForSet().size(key).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 判断集合是否包含value
     * @param key
     * @param value
     */
    public boolean isMember(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
