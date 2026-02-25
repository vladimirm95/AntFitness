package com.antfitness.ant.security;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jakarta.servlet.FilterChain;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LoginRateLimitFilterTest {

    @Test
    void shouldBlockAfterLimitExceeded() throws Exception {
        // Arrange: mock Redis
        StringRedisTemplate redis = mock(StringRedisTemplate.class);
        @SuppressWarnings("unchecked")
        ValueOperations<String, String> valueOps = mock(ValueOperations.class);
        when(redis.opsForValue()).thenReturn(valueOps);

        // simulate counter: 1..6 for same key
        AtomicLong counter = new AtomicLong(0);
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);

        when(valueOps.increment(keyCaptor.capture()))
                .thenAnswer(inv -> counter.incrementAndGet());

        LoginRateLimitFilter filter = new LoginRateLimitFilter(redis);

        // chain that marks when it was called
        final AtomicLong chainCalls = new AtomicLong(0);
        FilterChain chain = (req, res) -> chainCalls.incrementAndGet();

        String bodyJson = """
                {"usernameOrEmail":"test@example.com","password":"pass"}
                """;

        // Act: 5 allowed + 6th blocked
        for (int i = 1; i <= 6; i++) {
            MockHttpServletRequest req = new MockHttpServletRequest("POST", "/auth/login");
            req.setRemoteAddr("1.2.3.4");
            req.setContentType("application/json");
            req.setCharacterEncoding("UTF-8");
            req.setContent(bodyJson.getBytes(StandardCharsets.UTF_8));

            MockHttpServletResponse resp = new MockHttpServletResponse();

            filter.doFilter(req, resp, chain);

            if (i <= 5) {
                assertNotEquals(429, resp.getStatus(), "Attempt " + i + " should not be blocked");
            } else {
                assertEquals(429, resp.getStatus(), "Attempt 6 should be blocked");
                assertTrue(resp.getContentAsString().contains("Too many login attempts"),
                        "Response should contain rate limit message");
            }
        }

        // Assert: chain called exactly 5 times
        assertEquals(5, chainCalls.get(), "Request should pass filter 5 times, then be blocked");

        // Assert: expire called once (when count == 1)
        verify(redis, times(1)).expire(anyString(), eq(Duration.ofSeconds(60)));

        // Assert: all increments used the same key
        List<String> keys = keyCaptor.getAllValues();
        assertEquals(6, keys.size());
        assertTrue(keys.stream().allMatch(k -> k.equals(keys.get(0))), "All attempts should use same Redis key");
    }
}