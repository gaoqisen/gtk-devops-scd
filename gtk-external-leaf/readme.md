---
title: Leaf练习
date: 2020-02-04 19:50:11
tags: Leaf
categories: tool
description: 分布式全局ID
---

官方服务开源: [https://tech.meituan.com/2019/03/07/open-source-project-leaf.html](https://tech.meituan.com/2019/03/07/open-source-project-leaf.html)

官方ID生成: [https://tech.meituan.com/2017/04/21/mt-leaf.html](https://tech.meituan.com/2017/04/21/mt-leaf.html)

GitHub: [https://github.com/Meituan-Dianping/Leaf](https://github.com/Meituan-Dianping/Leaf)

## docker安装

```
// 克隆
git clone https://github.com/funtl/Leaf.git
cd Leaf
mvn clean install -DskipTests

// 构建
cd leaf-docker
chmod +x build.sh
./build.sh

// 运行
docker-compose up -d
```

## 使用

```
#segment号段模式。需要建立DB表，并配置leaf.jdbc.url, leaf.jdbc.username, leaf.jdbc.password如果不想使用该模式配置leaf.segment.enable=false即可
curl http://localhost:8080/api/segment/get/leaf-segment-test
#snowflake雪花模式。算法取自twitter开源的snowflake算法。如果不想使用该模式配置leaf.snowflake.enable=false即可
curl http://localhost:8080/api/snowflake/get/test
```


 