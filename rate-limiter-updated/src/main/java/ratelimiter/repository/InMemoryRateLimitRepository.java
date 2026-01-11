package ratelimiter.repository;
import ratelimiter.model.TokenBucket;
import java.util.concurrent.ConcurrentHashMap;
public class InMemoryRateLimitRepository implements RateLimitRepository {
    private final ConcurrentHashMap<String, TokenBucket> store = new ConcurrentHashMap<>();
    public TokenBucket get(String userId) { return store.get(userId); }
    public void put(String userId, TokenBucket bucket) { store.put(userId, bucket); }
}