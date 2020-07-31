docker run --restart=unless-stopped \
        -v /Users/Beto/Desktop/CursoSpring/data/redis:/data \
        --hostname redis.cedesistemas.local \
        --net cedesistemas_network \
        --name redis.cedesistemas.local \
        --memory=100m \
        -p 6379:6379 \
        -d redis redis-server \
        --appendonly yes
