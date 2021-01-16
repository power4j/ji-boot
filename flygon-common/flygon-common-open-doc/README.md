# OpenAPI 支持

Swagger2（基于openApi3）已经在17年停止维护了，取而代之的是 sagger3（基于openApi3），OpenApi是业界真正的 api 文档标准，其是由 Swagger 来维护的，并被linux列为api标准，从而成为行业标准。

## 相关概念

### Open API

OpenApi是业界真正的 api 文档标准，其是由 Swagger 来维护的，并被linux列为api标准，从而成为行业标准。

### Swagger

swagger 是一个 api 文档维护组织，后来成为了 Open API 标准的主要定义者，现在最新的版本为17年发布的 Swagger3（Open Api3）。`swagger2`的包名为 `io.swagger`，而`swagger3`的包名为 `io.swagger.core.v3`。


### SpringFox

SpringFox是 spring 社区维护的一个项目（非官方），帮助使用者将 swagger2 集成到 Spring 中。
常常用于 Spring 中帮助开发者生成文档，并可以轻松的在spring boot中使用。
截至2020年4月，都未支持 OpenAPI3 标准。


### SpringDoc

SpringDoc也是 spring 社区维护的一个项目（非官方），帮助使用者将 swagger3 集成到 Spring 中。
也是用来在 Spring 中帮助开发者生成文档，并可以轻松的在spring boot中使用。


## 应用层配置



```yaml
springdoc:
  version: '@project.version@'
  api-docs:
    enabled: true
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
    display-request-duration: true
    groups-order: DESC
    operationsSorter: method
    #oauth:
    #  clientId: newClient
    #  clientSecret: newClientSecret
  #oAuthFlow:
  #  authorizationUrl: http://localhost:8083/auth/realms/springdoc/protocol/openid-connect/auth
  #  tokenUrl: http://localhost:8083/auth/realms/springdoc/protocol/openid-connect/token
  show-actuator: true
  group-configs:
    - group: stores
      paths-to-match: /store/**
    - group: users
      packages-to-scan: org.springdoc.demo.app2
```

更多参考 ： https://springdoc.org/#properties