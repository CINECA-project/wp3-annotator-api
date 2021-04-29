# CINECA WP3 Concept Annotator Consolidated API
Consolidated API for querying Work Package 3 Text Mining pipelines.

Make sure you have Java 11+ installed in your system
## Spring Boot
```shell
./mvnw clean install -e
cd server
./mvnw spring-boot:run
```
***

## Spring Boot
###  Generate Spring Boot server using Docker
```shell
docker run --rm -v "${PWD}:/local" openapitools/openapi-generator-cli generate \
-i /local/api/open_api.yaml \
-g spring \
-c /local/api/config.json \
-o /local/server_spring_boot
```
### Run generated code
Docker creates output directory under root user. Reassign it to current user
```shell
sudo chown $USER server_spring_boot
```

Run generated code in the output directory
```shell
cd server_spring_boot
mvn spring-boot:run
```


:warning: **Ignore**
## Please ignore sections below this. Need to complete.
## Python
###  Generate Python server using Docker
```shell
java -jar swagger-codegen-cli-2.3.1.jar generate 
-i foo.yaml 
-l python-flask 
-o "swagger server\foo" 
-t swagger-codegen-master\modules\swagger-codegen\src\main\resources\flaskConnexio


docker run --rm -v "${PWD}:/local" openapitools/openapi-generator-cli generate \
-i /local/api/open_api.yaml \
-g python \
-c /local/api/config.json \
-o /local/server_python
```
### Run generated code
```shell
chown $USER server_python
cd server_python
mvn spring-boot:run
```

java -jar openapi-generator-cli-4.3.1.jar generate -g spring -i openapi.yaml -c conf.json -o spring-boot-codegenerator
