package com.antfitness.ant.security.aop;

import com.antfitness.ant.exceptions.ForbiddenException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import({AdminOnlyAspectTest.TestConfig.class, AdminOnlyAspect.class})
class AdminOnlyAspectTest {

    @Configuration
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    static class TestConfig {
        @Bean
        public DummyAdminService dummyAdminService() {
            return new DummyAdminService();
        }
    }

    public static class DummyAdminService {
        @AdminOnly
        public String adminAction() {
            return "ok";
        }
    }

    @Autowired
    DummyAdminService dummyAdminService;

    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void beanShouldBeAopProxy() {
        assertTrue(AopUtils.isAopProxy(dummyAdminService),
                "DummyAdminService should be proxied for AOP to work");
    }

    @Test
    void shouldThrowForbiddenForNonAdmin() {
        var auth = new UsernamePasswordAuthenticationToken(
                "user", "pass",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThrows(ForbiddenException.class, () -> dummyAdminService.adminAction());
    }

    @Test
    void shouldAllowAdmin() {
        var auth = new UsernamePasswordAuthenticationToken(
                "admin", "pass",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertEquals("ok", dummyAdminService.adminAction());
    }
}