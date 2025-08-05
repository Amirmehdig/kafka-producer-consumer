# Quick Start

1. **Build Producer and Consumer**

```bash
cd producer
mvn clean package

cd ../consumer
mvn clean package

cd ../compose
docker-compose up --build
```