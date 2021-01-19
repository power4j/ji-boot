# 任务调度 - Quartz 

常用配置
```yaml
spring:
  quartz:
    trace: false # 启用 trace 日志, 默认 false
    auto-startup: true # 自动启动调度器 默认 true
    job-store-type: jdbc #数据库方式
    wait-for-jobs-to-complete-on-shutdown: true # 应用关闭时,是否等待作业完成，默认 false
    jdbc:
      initialize-schema: never #不初始化表结构
    # Quartz 原生配置 
    properties:
      org:
        quartz:
          scheduler:
            instanceId: AUTO #默认主机名和时间戳生成实例ID,可以是任何字符串，但对于所有调度程序来说，必须是唯一的 对应qrtz_scheduler_state INSTANCE_NAME字段
            #instanceName: clusteredScheduler #quartzScheduler
          jobStore:
            class: org.quartz.impl.jdbcjobstore.JobStoreTX #持久化配置
            driverDelegateClass: org.quartz.impl.jdbcjobstore.StdJDBCDelegate #我们仅为数据库制作了特定于数据库的代理
            useProperties: false #以指示JDBCJobStore将JobDataMaps中的所有值都作为字符串，因此可以作为名称 - 值对存储而不是在BLOB列中以其序列化形式存储更多复杂的对象。从长远来看，这是更安全的，因为您避免了将非String类序列化为BLOB的类版本问题。
            tablePrefix: qrtz_  #数据库表前缀
            misfireThreshold: 60000 #在被认为“失火”之前，调度程序将“容忍”一个Triggers将其下一个启动时间通过的毫秒数。默认值（如果您在配置中未输入此属性）为60000（60秒）。
            clusterCheckinInterval: 5000 #设置此实例“检入”*与群集的其他实例的频率（以毫秒为单位）。影响检测失败实例的速度。
            isClustered: true #打开群集功能
          threadPool: #连接池
            class: org.quartz.simpl.SimpleThreadPool
            threadCount: 10
            threadPriority: 5
            threadsInheritContextClassLoaderOfInitializingThread: true
```

see https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html