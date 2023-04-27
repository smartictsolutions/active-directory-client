# Active Directory Client

***Active Directory Client*** is an active directory authenticator and user listing application that can easily configure and use. After the container image pulled and configure it, *Abracadabra* you can do active directory authentication and listing active directory users right away.

### Project Info

It is a Gradle-Spring Framework project that has two Rest endpoints. 

## Usage

### Configuration

##### Docker-Compose Configuration

	You can configure the service's key with using environment . The service will check firstly environment  , if its empty actually it is not set, then  it will check the "config.yml" file  . This check mechanism working one by one . if you wish you can set a few of them here and you can set the remaining keys configuration from config file. The Service always check here firstly . But be careful about the String operation. You can follow these examples . Check example docker-compose and config.yml file.

```yaml
version: '3.9'
services:
  active-directory-db:
    container_name: active-directory-db-container
    image: postgres:13.10
    restart: always
    environment:
      POSTGRES_USER: ACTIVEDIRECTORYUSER
      POSTGRES_PASSWORD : Prod_PassWord_06_!
      POSTGRES_DB: ACTIVE_DIRECTORY_DB
    command:
      - "postgres"
      - "-c"
      - "wal_level=logical"
    ports:
      - "5432:5432"
    volumes:
      - ./active_directory_db_data:/var/lib/postgresql/data
    networks:
      active-directory-net:
        ipv4_address: 162.24.0.2

  active-directory-app:
    depends_on:
      - active-directory-db
    # veritabanının hazır olmasını bekliyoruz.
    command: bash -c "while !</dev/tcp/162.24.0.2/5432; do sleep 5; done; java -jar -Dspring.config.location=file:/config/active-directory-config.yml /active-directory.jar"
    container_name: active-directory-app-container
    image: active-directory-app
    restart: always
    ports:
      - "7003:7000"
    volumes:
      - ./exposed_config/active-directory-app-config-base.yml:/config/active-directory-config.yml
    logging:
      driver: "json-file"
      options:
        max-size: "100m"
        max-file: "10"
    networks:
      active-directory-net:
        ipv4_address: 162.24.0.3

networks:
  active-directory-net:
    name: active-directory-net
    ipam:
      driver: default
      config:
        - subnet: 162.24.0.0/16
          gateway: 162.24.0.1
```

  ##### Application YAML Based Configuration

```yaml
server:
  port: 7003

spring:
  datasource:
    url: jdbc:postgresql://162.24.0.2:5432/ACTIVE_DIRECTORY_DB
    username: ACTIVEDIRECTORYUSER
    password: Prod_PassWord_06_!
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 5
  liquibase:
    change-log: classpath:/liquibase/active_directory_changelog-v1.0.xml
  jpa:
    hibernate:
      ddl-auto: none
    show-sql: true
    database: POSTGRESQL
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        enable_lazy_load_no_trans: true
        temp:
          use_jdbc_metadata_defaults: false
active-directory:
  is-test-env: false
  security:
    cors:
      allowed-hosts:
        - /**
```

####  **server** 

 - **port:** Application's serving port.

>   Note: This port must be exact same value on the docker-compose ***<ports>*** 's value.

#### **activedirectory\security\cors**

- **allowed-hosts:**  You can configure the allowed host ports. It is for request of other services .Ex if you enter "7000" to configuration , only the service that is working on "7000" port can send request to this client.

### Docker

- ```shell
  docker pull smartsolutions/active-directory-client-image:latest
  docker run --name active-directory-client-container -p 7003:7003 -d smartsolutions/active-directory-client-image:latest
  ```

- Install with docker-compose

    First of all, you must create a docker-compose file. Depends on the configuration choice   



## API Endpoints 

**GET  activedirectory/getAllUsers/{customerId}/{ldapQueryKey}**

This endpoint returns your active directory user. The ldap key and customer identification parameters are required.



**POST  activedirectory/authenticate**

This endpoint will authenticate user with username and password from Active Directory.

```json
{
	"username":"username",
	"password":"password"
}
```

**GET  activedirectory/getAllUsers**

This endpoint will send all active directory users, you can achieve from Active Directory.



**POST  activedirectory/getUsersContainsWithKeyValue**

This endpoint will send , users data that is defined with key and value (simple ldap Query) .
Ex. if you set the key="sAMAccountName" and set the value="a" , this will return users with contains "a" in their username.
if you set Key is null , It will be set "sAMAccountName" by default.
if you set Value is null , It will be set "*" by default.

```json
{	"customerId": "$customerId",
	"key":"sAMAccountName",
	"value":"a"
}
```

**POST  activedirectory/setActiveDirectorySetting**

This endpoint is used to save the settings when you want to add a new active directory settings.

**GET activedirectory/readActiveDirectorySettingByCustomerId/{customerId}**

This endpoint returns you the active directory settings with the customer ID.

**DELETE activedirectory/deleteActiveDirectorySettingByCustomerId/{customerId}**

This endpoint deletes the customer ID and directory settings.

**RESPONSE**

For Response , service is using  ActiveDirectoryUser Class for user data in RestResponse Class (ResponseEntity<RestResponse<ActiveDirectoryUser>>) . So you should catch the response with RestResponse Class .

Response of "getAllUser" and "getUsersContainsWithKeyValue" is list of ActiveDirectoryUser in RestResponse Class's  data T. Actualy it is almost same with "/authentication". The different thing is ; RestResponse data type . Data T is  , "List<ActiveDirectoryUser>" . 

If something goes wrong (if you get exception) . RestResponse data will be null and you get the error . You can check the error details from RestResponse object's messages .


```java
    public class RestResponse<T> {
        private T data;
        private String titleLanguageKey;
        private String title;
        private String messageLanguageKey;
        private String message;
	      private ResponseTypeEnum type;
  
        public RestResponse() {}
  
        public RestResponse(T data, ResponseTypeEnum type) {
	          this.data = data;
	          this.type = type;
	      }
	
	      public RestResponse(String messageLanguageKey, String message, ResponseTypeEnum type) {
	          this.title = "";
	          this.titleLanguageKey = "";
	          this.message = message;
	          this.messageLanguageKey = messageLanguageKey;
	          this.type = type;
	      }
	
	      public RestResponse(String titleLanguageKey, String title, String messageLanguageKey, String message, ResponseTypeEnum     type) {
	          this.title = title;
	          this.titleLanguageKey = titleLanguageKey;
	          this.message = message;
	          this.messageLanguageKey = messageLanguageKey;
	          this.type = type;
	      }
	
	      public RestResponse(T data, String titleLanguageKey, String title, String messageLanguageKey, String message, ResponseTypeEnum type) {
	          this.data = data;
	          this.title = title;
	          this.titleLanguageKey = titleLanguageKey;
	          this.message = message;
	          this.messageLanguageKey = messageLanguageKey;
	          this.type = type;
	  }
```
```java
	public enum ResponseTypeEnum {
	  Success,
	  Warning,
	  Info,
	  Error
	}
```

```java
	public class ActiveDirectoryUser{
	    private String username;
	    private String name;
	    private String surname;
	    private String eposta;
	    private String mobilePhone;
	    private String address;
	    private String title;
	    private String employeeNo;
	}
```


## 	Quick Reference

	    - [Github repository](https://github.com/smartictsolutions/active-directory-client)
	    - [Docker Repository](https://hub.docker.com/r/smartictsolutions/active-directory-client)
	    - [About us](https://www.smartict.com.tr/)
