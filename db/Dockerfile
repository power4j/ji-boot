FROM mysql:8.0.21
LABEL maintainer="jclazz@outlook.com"

ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./*.sql /docker-entrypoint-initdb.d/
