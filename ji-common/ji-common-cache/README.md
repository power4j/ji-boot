# 缓存模块
基于`spring-boot-starter-cache` , 支持多种缓存后端(JCache,EhCache,Redis,Caffeine),切换缓存方案与应用层。

## 应用层配置

参考：[Cache Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#common-application-properties-cache), 或者`org.springframework.boot.autoconfigure.cache.CacheProperties`

## 注解增强
使用注解时，可以在缓存名称上加有效期后缀,比如`#1D`，井号是有效期标识符，井号后面的数字是时长，字母D是时间单位，时间单位有`D,H,M,S`，分别表示`天,小时,分钟,秒`,单位字母不区分大小写。

例子

```java
@Cacheable(cacheNames = "user_role#30s",key = "#username")
public List<SysRole> listForUser(String username, String grantType) {
    // 
}
```
在代码中设置的有效期，优先级高于默认配置

## Caffeine 配置

```yaml
ji-boot:
  # 缓存配置
  cache:
    type: caffeine
    caffeine:
      spec: "expireAfterWrite=24h,maximumSize=100000"
```

## redisson 配置

```yaml
spring:
  redis:
    database: ${REDIS_DB:2}
    host: ${REDIS_HOST:127.0.0.1}
    port: ${REDIS_PORT:6379}
    timeout: 5s
    ssl: false
    redisson:
      # 可以从文件加载redisson 配置
      #file: classpath:redisson.yaml
      # 或者直接定义
      config: |
        clusterServersConfig:
          idleConnectionTimeout: 10000
          connectTimeout: 10000
          timeout: 3000
          retryAttempts: 3
          retryInterval: 1500
          failedSlaveReconnectionInterval: 3000
          failedSlaveCheckInterval: 60000
          password: null
          subscriptionsPerConnection: 5
          clientName: null
          loadBalancer: !<org.redisson.connection.balancer.RoundRobinLoadBalancer> {}
          subscriptionConnectionMinimumIdleSize: 1
          subscriptionConnectionPoolSize: 50
          slaveConnectionMinimumIdleSize: 24
          slaveConnectionPoolSize: 64
          masterConnectionMinimumIdleSize: 24
          masterConnectionPoolSize: 64
          readMode: "SLAVE"
          subscriptionMode: "SLAVE"
          nodeAddresses:
          - "redis://127.0.0.1:7004"
          - "redis://127.0.0.1:7001"
          - "redis://127.0.0.1:7000"
          scanInterval: 1000
          pingConnectionInterval: 0
          keepAlive: false
          tcpNoDelay: false
        threads: 16
        nettyThreads: 32
        codec: !<org.redisson.codec.MarshallingCodec> {}
        transportMode: "NIO"
      
ji-boot:
  # 缓存配置
  cache:
    type: redisson
    redisson:
      ttl: 12h
      max-idle-time: 2h
      max-size: 0
```

redisson 配置参考: [redisson wiki](https://github.com/redisson/redisson/wiki/2.-%E9%85%8D%E7%BD%AE%E6%96%B9%E6%B3%95)

## 无缓存 配置

```yaml
ji-boot:
  cache:
    type: none
```
