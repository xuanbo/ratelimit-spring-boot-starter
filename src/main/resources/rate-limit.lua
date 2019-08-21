-- 限流key
local key = KEYS[1]
-- 单位时间对key限流的次数
local limit = tonumber(ARGV[1])
-- 过期时间
local expire = tonumber(ARGV[2])

-- 自增后的次数
local incrLimit = redis.call('incr', key)

-- key过期时间
local ttl = redis.call('ttl', key)

-- 首次调用，设置key过期时间
if incrLimit == 1 then
    redis.call('expire', key, expire)
else
    -- ttl是为防止某些key在未设置超时时间，并长时间已经存在的情况下做的保护的判断
    if ttl == -1 then
        redis.call('expire', key, expire)
    end
end

-- 超过限流次数，返回0
if incrLimit > limit then
    return "0"
end

-- 未达到限流次数，返回1
return "1"