## about project
```
A simple spring boot project using spring thymleaf for front end - it validates existing customers phones and detect the country they belong to by the code in phones
You can filter by state of phone number whether it is VALID or INVALID .. you can combine the filtration with country name from five countries
Unit tests are already done for this project with mocked dependencies to run in isolation and have single responsibility priniciple achieved
```

## Steps to run project
```
mvn clean install
docker build -t phone-validator-image .
docker run -d -p 8080:8080 --name phone-validator-container phone-validator-image:latest

navigate to http://localhost:8080/

or test with postman:

http://localhost:8080/api/v1/phone-validator?state=VALID&countries=Morocco,Mozambique
```
