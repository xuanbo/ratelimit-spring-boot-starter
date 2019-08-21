package tk.fishfish.ratelimit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.fishfish.ratelimit.autoconfigure.RateLimitAutoConfiguration;

/**
 * 限流测试
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        RateLimitAutoConfiguration.class,
        RedisAutoConfiguration.class
})
public class RateLimitTest {

    @Autowired
    private RateLimit rateLimit;

    @Test
    public void run() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            System.out.println(rateLimit.tryAcquire("service:1", 10L, 1L));
            Thread.sleep(90L);
        }
    }

}
