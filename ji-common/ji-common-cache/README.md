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

开启缓存配置

```yaml
spring:
  cache:
    # 缓存类型 Caffeine
    type: caffeine
    caffeine:
      # 默认策略: 缓存过期时间,最大缓存数量
      spec: "expireAfterWrite=24h,maximumSize=100000"
```
