# ratelimit-spring-boot-starter

> spring boot限流starter

## 快速开始

`starter`提供`RateLimit`bean，底层封装了lua脚本，用于决定是否能访问。

如下是接口说明：

```java
package tk.fishfish.ratelimit;

/**
 * 限流
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public interface RateLimit {

    /**
     * 尝试获取
     *
     * @param key    获取的key
     * @param limit  单位时间允许的数量
     * @param expire 单位时间，折算成秒
     * @return 获取到返回true
     */
    boolean tryAcquire(String key, Long limit, Long expire);

}
```

接口比较简单，未提供太多功能，比如一些api的限流次数等信息，均需要使用者自己维护。

### 依赖

jar已发布到中仓仓库，直接依赖即可。

```xml
<dependency>
    <groupId>tk.fishfish</groupId>
    <artifactId>ratelimit-spring-boot-starter</artifactId>
    <version>1.0.0.RELEASE</version>
</dependency>
```

### 使用

下面是一个测试，使用时直接注入`RateLimit`即可。

```java
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
            if (rateLimit.tryAcquire("service:1", 10L, 1L)) {
                System.out.println("访问成功")
            }
            Thread.sleep(90L);
        }
    }

}

```

## 说明

本starter不支持api限流规则维护管理，只是一个限流工具，不是一个限流平台。

当然，有兴趣的道友可以一起完善。
