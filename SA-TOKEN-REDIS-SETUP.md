# Sa-Token Redis 配置指南

## 概述

本文档说明如何在微服务架构中配置Sa-Token使用Redis存储登录状态，实现各服务间的会话共享。

## 配置步骤

### 1. 添加依赖

在需要使用Sa-Token的微服务pom.xml中添加以下依赖：

```xml
<!-- Sa-Token响应式集成 -->
<dependency>
    <groupId>cn.dev33</groupId>
    <artifactId>sa-token-reactor-spring-boot-starter</artifactId>
</dependency>
<!-- Sa-Token 整合 Redis (使用jackson序列化方式) -->
<dependency>
    <groupId>cn.dev33</groupId>
    <artifactId>sa-token-redis-jackson</artifactId>
</dependency>
<!-- Redis连接池 -->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
</dependency>
<!-- Redis客户端 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

### 2. 配置Nacos共享配置

将`common-sa-token.yml`文件上传到Nacos配置中心，包含以下内容：

```yaml
# Sa-Token共享配置
sa-token:
  # token名称 (同时也是cookie名称)
  token-name: Authorization
  # token有效期，单位s 默认30天, -1代表永不过期
  timeout: 2592000
  # token临时有效期 (指定时间内无操作就视为token过期) 单位: 秒
  active-timeout: -1
  # 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
  is-concurrent: true
  # 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)
  is-share: false
  # token风格
  token-style: uuid
  # 是否输出操作日志
  is-log: false
  # 是否从cookie中读取token
  is-read-cookie: false
  # 是否从header中读取token
  is-read-header: true
  # token前缀
  token-prefix: Bearer

# Redis配置
spring:
  redis:
    host: localhost
    port: 6379
    password: 
    database: 0
    timeout: 10000ms
    lettuce:
      pool:
        max-active: 8
        max-wait: -1ms
        max-idle: 8
        min-idle: 0
```

### 3. 配置bootstrap.yml

在微服务的bootstrap.yml中添加对共享配置的引用：

```yaml
spring:
  application:
    name: [服务名]
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        username: nacos
        password: nacos
      config:
        server-addr: 127.0.0.1:8848
        file-extension: yml
        shared-configs:
          - data-id: common-sa-token.yml
            group: DEFAULT_GROUP
            refresh: true
```

### 4. 添加Redis配置类

在微服务中添加SaTokenRedisConfig配置类：

```java
package com.soybean.[模块名].config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Sa-Token Redis配置
 */
@Configuration
public class SaTokenRedisConfig {

    /**
     * 配置RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }
}
```

## 注意事项

1. 确保Redis服务已启动并可访问
2. 根据实际环境调整Redis连接参数（主机、端口、密码等）
3. 在生产环境中，建议配置Redis的持久化和高可用方案
4. 定期清理Redis中的过期会话数据，避免内存占用过高

## 工作原理

1. 用户登录后，Sa-Token将会话信息存储在Redis中
2. 各微服务通过Redis共享这些会话信息
3. 当用户发起请求时，Sa-Token从Redis中验证会话状态
4. 通过这种方式，实现了微服务架构下的单点登录和会话共享
