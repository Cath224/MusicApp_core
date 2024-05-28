package ru.musicapp.coreservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.musicapp.coreservice.model.dto.playlist.PlaylistQueueElement;

@Configuration
public class RedisConfiguration {

    @Bean("playlistQueueRedisTemplate")
    public RedisTemplate<String, PlaylistQueueElement> playlistQueueElementRedisTemplate(final RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, PlaylistQueueElement> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

}
