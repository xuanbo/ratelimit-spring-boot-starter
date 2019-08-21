package tk.fishfish.ratelimit.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import tk.fishfish.ratelimit.RateLimit;
import tk.fishfish.ratelimit.RedisRateLimit;

/**
 * 限流自动配置
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@Configuration
public class RateLimitAutoConfiguration {

    /**
     * 注册限流bean
     *
     * @param redisTemplate StringRedisTemplate
     * @return RedisRateLimit
     */
    @Bean
    public RateLimit fishRateLimit(StringRedisTemplate redisTemplate) {
        return new RedisRateLimit(redisTemplate);
    }

}
