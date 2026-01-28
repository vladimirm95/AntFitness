package com.antfitness.ant.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class LoginRateLimitFilter extends OncePerRequestFilter {

    private final StringRedisTemplate redis;

    // npr. 5 pokušaja u 60 sekundi
    private static final int LIMIT = 5;
    private static final Duration WINDOW = Duration.ofSeconds(60);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // samo login endpoint
        if (isLoginRequest(request)) {
            String ip = getClientIp(request);
            String key = "rl:login:ip:" + ip;

            Long count = redis.opsForValue().increment(key);

            // postavljam TTl
            if (count != null && count == 1L) {
                redis.expire(key, WINDOW);
            }

            if (count != null && count > LIMIT) {
                response.setStatus(429);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write("{\"message\":\"Too many login attempts. Please try again later.\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isLoginRequest(HttpServletRequest request) {
        return "POST".equalsIgnoreCase(request.getMethod())
                && request.getRequestURI().equals("/auth/login");
    }

    // Radi lokalno + radi iza reverse proxy (ako prosledi X-Forwarded-For)
    private String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
