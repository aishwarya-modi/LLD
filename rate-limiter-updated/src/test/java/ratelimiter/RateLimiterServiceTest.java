package ratelimiter;
import org.junit.jupiter.api.Test;
import ratelimiter.repository.InMemoryRateLimitRepository;
import ratelimiter.service.RateLimiterService;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
public class RateLimiterServiceTest {
    @Test
    public void testLimit() {
        RateLimiterService service = new RateLimiterService(new InMemoryRateLimitRepository(), 2, 1000);
        assertTrue(service.allowRequest("u"));
        assertTrue(service.allowRequest("u"));
        assertFalse(service.allowRequest("u"));
    }
    @Test
    public void testRefill() throws Exception {
        RateLimiterService service = new RateLimiterService(new InMemoryRateLimitRepository(), 1, 100);
        assertTrue(service.allowRequest("u"));
        assertFalse(service.allowRequest("u"));
        Thread.sleep(150);
        assertTrue(service.allowRequest("u"));
    }
    @Test
    public void testConcurrency() throws Exception {
        RateLimiterService service = new RateLimiterService(new InMemoryRateLimitRepository(), 50, 1000);
        int threads = 20;
        ExecutorService ex = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);
        for (int i = 0; i < threads; i++) {
            ex.submit(() -> { service.allowRequest("u"); latch.countDown(); });
        }
        latch.await();
        ex.shutdown();
        int allowed = 0;
        for (int i = 0; i < 50; i++) if (service.allowRequest("u")) allowed++;
        assertTrue(allowed <= 50);
    }
}