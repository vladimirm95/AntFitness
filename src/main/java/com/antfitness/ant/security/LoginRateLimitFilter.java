package com.antfitness.ant.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginRateLimitFilter extends OncePerRequestFilter {

    private final StringRedisTemplate redis;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final int LIMIT = 5;
    private static final Duration WINDOW = Duration.ofSeconds(60);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isLoginRequest(request)) {

            CachedBodyHttpServletRequest wrapped = new CachedBodyHttpServletRequest(request);

            String ip = getClientIp(wrapped);
            String userIdentifier = extractUserIdentifierFromJson(wrapped.getCachedBodyAsString());

            String userKey = (userIdentifier == null || userIdentifier.isBlank())
                    ? "unknown"
                    : hashKey(userIdentifier.trim().toLowerCase());

            String key = "rl:login:ipuser:" + ip + ":" + userKey;

            Long count = redis.opsForValue().increment(key);
            if (count != null && count == 1L) {
                redis.expire(key, WINDOW);
            }

            if (count != null && count > LIMIT) {
                response.setStatus(429);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write("{\"message\":\"Too many login attempts for this account from this IP. Try again later.\"}");
                return;
            }

            filterChain.doFilter(wrapped, response);
            return;
        }

        filterChain.doFilter(request, response);
    }


    private boolean isLoginRequest(HttpServletRequest request) {
        return "POST".equalsIgnoreCase(request.getMethod())
                && request.getRequestURI().equals("/auth/login");
    }

    private String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    /**
     * Čita JSON body i pokušava da nađe field za login.
     * Podržava nazive: usernameOrEmail, email, username
     */
    private String extractUserIdentifierFromJson(String json) {
        try {
            if (json == null || json.isBlank()) return null;

            @SuppressWarnings("unchecked")
            Map<String, Object> map = objectMapper.readValue(json, Map.class);

            Object v = map.get("usernameOrEmail");
            if (v == null) v = map.get("email");
            if (v == null) v = map.get("username");

            return v == null ? null : String.valueOf(v);
        } catch (Exception e) {
            return null;
        }
    }


    private String hashKey(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            // kraći “url-safe” string
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception e) {
            // fallback
            return input;
        }
    }
}
