## 一、安装SkyWalking

### 安装ElasticSearch

```yml
version: '3.3'
services:
  elasticsearch:
    image: wutang/elasticsearch-shanghai-zone:6.3.2
    container_name: elasticsearch
    restart: always
    ports:
      - 9200:9200
      - 9300:9300
    environment:
      cluster.name: elasticsearch
```

> 需要用docker的地址（docke inspect id）获取docker容器的ip。如果填写localhost会出现com.netflix.zuul.exception.ZuulException: Forwarding error的报错信息。（花了2个小时解决问题）

访问http://localhost:9200/出现json字符串表示安装并启动成功。

### 下载SkyWalking

下载地址: [http://skywalking.apache.org/zh/downloads/](http://skywalking.apache.org/zh/downloads/)

### 更改配置

下载的gz包解压之后，在根目录的config目录里面找到application.yml并修改配置

![https://gaoqisen.github.io/GraphBed/202002/20200202132006.png](https://gaoqisen.github.io/GraphBed/202002/20200202132006.png)

### 启动

在根目录的bin目录里面执行startup.sh或者startup.bat启动程序。启动程序成功之后，访问http://localhost:8080/出现以下页面表示成功。
![https://gaoqisen.github.io/GraphBed/202002/20200202132603.png](https://gaoqisen.github.io/GraphBed/202002/20200202132603.png)

## 使用SkyWalking

### idea启动方式

在此处添加以下内容
```
-javaagent:/Users/jasongao/Documents/workspace/gtk-devops-scd/gtk-external-skywalking/agent/skywalking-agent.jar
-Dskywalking.agent.service_name=nacos-provider
-Dskywalking.collector.backend_service=localhost:11800
```

![https://gaoqisen.github.io/GraphBed/202002/20200202134128.png](https://gaoqisen.github.io/GraphBed/202002/20200202134128.png)

> 只需要添加环境变量即可，之后启动程序会自动实现链路追踪

### java启动方式

```
java -javaagent:/Users/jasongao/Documents/workspace/gtk-devops-scd/gtk-external-skywalking/agent/skywalking-agent.jar -Dskywalking.agent.service_name=nacos-provider -Dskywalking.collector.backend_service=localhost:11800 -jar yourApp.jar
```

### 链路追踪之后

![https://gaoqisen.github.io/GraphBed/202002/20200202135135.png](https://gaoqisen.github.io/GraphBed/202002/20200202135135.png)