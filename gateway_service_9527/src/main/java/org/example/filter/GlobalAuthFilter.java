package org.example.filter;


import io.jsonwebtoken.Claims;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
@Order(0)
public class GlobalAuthFilter implements GlobalFilter {
    @Autowired
    private JwtUtils jwtUtils;
    private static final List<Pattern> EXCLUDED_PATTERNS = Arrays.asList(
            Pattern.compile("/user/login"),
            Pattern.compile("/user/register"),
            Pattern.compile("/business/"),
            Pattern.compile("/business/orderTypeId/\\d+"),
            Pattern.compile("/business/search/.+"),
            Pattern.compile("/business/businessId/\\d+"),
            Pattern.compile("/food/\\d+/\\d+"),
            Pattern.compile("/food/businessId/\\d+")
    );
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        // 判断当前路径是否需要排除
        if (isExcludedPath(path)) {
            return chain.filter(exchange);
        }

        String token = extractTokenFromRequest(exchange);

        if (token == null) {
            return unauthorizedResponse(exchange, "请先登录");
        }

        return chain.filter(exchange);
    }
    private boolean isExcludedPath(String path) {
        return EXCLUDED_PATTERNS.stream().anyMatch(pattern -> pattern.matcher(path).matches());
    }
    private String extractTokenFromRequest(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().getFirst("Authorization");
    }
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] bytes = ("{\"message\": \"" + message + "\"}").getBytes(StandardCharsets.UTF_8);
        exchange.getResponse().getHeaders().setContentLength(bytes.length);
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
    }
}