## How to bootstrap
```bash
./mvnw package # use maven to package the spring application
docker compose up -d --build
```

Manual operations steps:
### Start or rebuild the Docker MySQL container

### Delete old containers and data volumes
```bash
docker rm -f pes-mysql
docker volume prune -f 
```

### Rebuild the image
```bash
docker build -f dbDockerfile -t pes-mysql .
```

### Run the container again
```bash
docker run -d --name pes-mysql -p 3306:3306 pes-mysql
```

### Check logs
```bash
docker logs -f pes-mysql
```

Run `PesApplication.java` and wait for the application to start.
Then visit: http://localhost:8080/health/db/full

If the following message appears, it means that the Docker MySQL connection is successful:
```bash
Connected to DB: professorevaluation | student=? | review=? | course=? | professor=?
```