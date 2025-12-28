// package com.example.branch.config;


// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
// import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
// import org.springframework.data.redis.serializer.StringRedisSerializer;

// import com.example.branch.entity.Branch;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.json.JsonMapper;
// import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// // Redis configuration disabled - using basic database operations only
// //@Configuration
// public class BranchJedisConfig {
    
//     @Value("${spring.redis.host}")
//     private String redisHost;
    
//     @Value("${spring.redis.port}")
//     private int redisPort;
    
//     @Bean
//     JedisConnectionFactory jedisConnectionFactory() {
//         RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//         redisStandaloneConfiguration.setHostName(redisHost);
//         redisStandaloneConfiguration.setPort(redisPort);
        
//         JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
//         jedisConFactory.afterPropertiesSet();
        
//         return jedisConFactory;
//     }

//     @Bean
//     public ObjectMapper objectMapper() {
//         ObjectMapper mapper = JsonMapper.builder()
//             .findAndAddModules()
//             .build();
//         mapper.registerModule(new JavaTimeModule());
//         return mapper;
//     }

//     @Bean 
//     public RedisTemplate<String, Branch> redisTemplate(ObjectMapper objectMapper) {
//         RedisTemplate<String, Branch> template = new RedisTemplate<String, Branch>();

//         Jackson2JsonRedisSerializer<Branch> jsonSerializer = 
//             new Jackson2JsonRedisSerializer<>(objectMapper, Branch.class);
//         template.setConnectionFactory(jedisConnectionFactory());

//         template.setKeySerializer(new StringRedisSerializer());
//         template.setHashKeySerializer(new StringRedisSerializer());

//         template.setHashValueSerializer(jsonSerializer);
//         template.setValueSerializer(jsonSerializer);
        
//         return template;
//     }
// }
