#!/bin/bash

read -p "Build and push? [yN]" -n 1 -r
echo    # (optional) move to a new line
if [[ ! $REPLY =~ ^[Yy]$ ]]
then
    [[ "$0" = "$BASH_SOURCE" ]] && exit 1 || return 1 # handle exits from shell or function but don't exit interactive shell
fi


docker build -t registry.cn-hangzhou.aliyuncs.com/power4j/ji-admin-db ./db
docker push registry.cn-hangzhou.aliyuncs.com/power4j/ji-admin-db
docker build -t registry.cn-hangzhou.aliyuncs.com/power4j/ji-admin-server ./ji-admin
docker push registry.cn-hangzhou.aliyuncs.com/power4j/ji-admin-server