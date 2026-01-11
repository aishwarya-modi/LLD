package ratelimiter.model;
public class TokenBucket {
    public int tokens;
    public long lastRefillTimestamp;
    public TokenBucket(int tokens, long lastRefillTimestamp) {
        this.tokens = tokens;
        this.lastRefillTimestamp = lastRefillTimestamp;
    }
}