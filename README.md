## Steps to run project
```
mvn clean install
docker build -t phone-validator-image .
docker run -d -p 8080:8080 --name phone-validator-container phone-validator-image:latest

navigate to http://localhost:8080/
```
