package tk.fishfish.ratelimit;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Collections;

/**
 * 基于redis实现限流
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class RedisRateLimit implements RateLimit {

    private final StringRedisTemplate redisTemplate;
    private final RedisScript<String> redisScript;

    public RedisRateLimit(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisScript = newRedisScript();
    }

    @Override
    public boolean tryAcquire(String key, Long limit, Long expire) {
        // 执行限流lua脚本
        String result = redisTemplate.execute(
                redisScript,
                Collections.singletonList(key),
                String.valueOf(limit), String.valueOf(expire)
        );
        if (result == null) {
            return false;
        }
        int value = Integer.parseInt(result);
        return value == 1;
    }

    private RedisScript<String> newRedisScript() {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("rate-limit.lua")));
        redisScript.setResultType(String.class);
        return redisScript;
    }

}
