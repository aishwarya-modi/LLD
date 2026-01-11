package ratelimiter.service;
import ratelimiter.model.TokenBucket;
import ratelimiter.repository.RateLimitRepository;
public class RateLimiterService {
    private final RateLimitRepository repository;
    private final int maxTokens;
    private final long refillIntervalMillis;
    public RateLimiterService(RateLimitRepository repository, int maxTokens, long refillIntervalMillis) {
        this.repository = repository;
        this.maxTokens = maxTokens;
        this.refillIntervalMillis = refillIntervalMillis;
    }
    public boolean allowRequest(String userId) {
        long now = System.currentTimeMillis();
        synchronized (userId.intern()) {
            TokenBucket bucket = repository.get(userId);
            if (bucket == null) {
                repository.put(userId, new TokenBucket(maxTokens - 1, now));
                return true;
            }
            if (now - bucket.lastRefillTimestamp >= refillIntervalMillis) {
                bucket.tokens = maxTokens;
                bucket.lastRefillTimestamp = now;
            }
            if (bucket.tokens > 0) {
                bucket.tokens--;
                return true;
            }
            return false;
        }
    }
}