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
