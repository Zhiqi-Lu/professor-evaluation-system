## How to bootstrap
```bash
./mvnw package # use maven to package the spring application
docker compose up -d --build
```

Manual operations steps:
### 启动/重建Docker MySQL

### 删掉旧容器和旧数据卷
```bash
docker rm -f pes-mysql
docker volume prune -f 
```

### 重新build镜像
```bash
docker build -f dbDockerfile -t pes-mysql .
```

### 重新运行容器
```bash
docker run -d --name pes-mysql -p 3306:3306 pes-mysql
```

### 查看日志
```bash
docker logs -f pes-mysql
```

运行 PesApplication.java 等待启动完成  
访问 http://localhost:8080/health/db/full

出现以下内容则意味着Docker MySQL连接成功
```bash
Connected to DB: professorevaluation | student=? | review=? | course=? | professor=?
```