package ratelimiter.repository;
import ratelimiter.model.TokenBucket;
public interface RateLimitRepository {
    TokenBucket get(String userId);
    void put(String userId, TokenBucket bucket);
}