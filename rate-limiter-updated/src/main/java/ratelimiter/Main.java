package ratelimiter;
import ratelimiter.repository.InMemoryRateLimitRepository;
import ratelimiter.service.RateLimiterService;
public class Main {
    public static void main(String[] args) throws Exception {
        RateLimiterService service =
            new RateLimiterService(new InMemoryRateLimitRepository(), 3, 1000);
        System.out.println(service.allowRequest("user1"));
        System.out.println(service.allowRequest("user1"));
        System.out.println(service.allowRequest("user1"));
        System.out.println(service.allowRequest("user1"));
        Thread.sleep(1100);
        System.out.println(service.allowRequest("user1"));
    }
}