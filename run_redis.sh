docker run --restart=unless-stopped \
        -v /var/data/redis:/data \
        --hostname redis.eladio.local \
        --name redis.eladio.local \
        --memory=100m \
        -p 6379:6379 \
        -d redis redis-server \
        --appendonly yes
