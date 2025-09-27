package br.com.fiap.msnotificacao.cache;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class EventIdempotencyChecker {

    private final StringRedisTemplate redisTemplate;

    public EventIdempotencyChecker(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean validarERegistrarEvento(String eventId, Duration ttl) {
        Boolean isNew = redisTemplate.opsForValue().setIfAbsent(eventId, "OK", ttl);
        return Boolean.TRUE.equals(isNew);
    }

}
