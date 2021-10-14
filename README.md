# Active Directory Client

***Active Directory Client*** is an active directory authenticator and user listing application that can easily configure and use. After the container image pulled and configure it, *Abracadabra* you can do active directory authentication and listing active directory users right away.

### Project Info

It is a Gradle-Spring Framework project that has two Rest endpoints. 

## Usage

### Configuration

##### Docker-Compose Configuration

	You can configure the service's key with using environment . The service will check firstly environment  , if its empty actually it is not set, then  it will check the "config.yml" file  . This check mechanism working one by one . if you wish you can set a few of them here and you can set the left key configurations from config file. The Service always check here firstly . But be careful about the String operation. You can follow these examples . Check example docker-compose and config.yml file.

```yaml
version: '3.9'
services:
  active-directory-client:
    container_name: active-directory-client-container
    image: active-directory-client-image
    ports:
      - "7003:7003"
   	environment:
      PRINCIPAL: "@smartict.com.tr"
      DOMAIN_CONTROLLERS: "dc=smartict,dc=com,dc=tr"
      ORGANIZAION_UNIT: "ou=Kullanicilar,""
      SECURITY_AUTHENTICATION: simple
      FILTER_QUERY: "(objectClass=*)"
      PROVIDER_URL: "ldap://192.168.10.50:389"
      USERNAME_KEY: sAMAccountName
      NAME_KEY: givenName
      SURNAME_KEY: sn
      MAIL_KEY: mail
      MOBILE_KEY: telephoneNumber
      ADDRESS_KEY: streetAddress
      TITLE_KEY: title
      PID_KEY: employeeNumber
    volumes:
      - ./exposed_config/active-directory-client-config-base.yml:/config/active-directory-client-config.yml
    networks:
      active-directory-client-net:
        ipv4_address: 152.24.0.3

networks:
  active-directory-client-net:
    name: active-directory-client-net
    ipam:
      driver: default
      config:
        - subnet: 152.24.0.0/16
          gateway: 152.24.0.1
```

  So if you want to use environment from docker-compose you need to know corresponding configuration from config.yml (Application YAML);

PRINCIPAL correspond to principal in ".yml" file.

DOMAIN_CONTROLLERS correspond to domain-controllers in ".yml" file.

ORGANIZAION_UNIT correspond to organization-unit in ".yml" file.

SECURITY_AUTHENTICATION correspond to security-authentication in ".yml" file.

FILTER_QUERY correspond to filter-query in ".yml" file.

PROVIDER_URL correspond to provider-url in ".yml" file.

USERNAME_KEY correspond to username-key in ".yml" file.

NAME_KEY correspond to name-key in ".yml" file.

SURNAME_KEY correspond to surname-key in ".yml" file.

MAIL_KEY correspond to mail-key in ".yml" file.

MOBILE_KEY correspond to mobile-key in ".yml" file.

ADDRESS_KEY correspond to address-key in ".yml" file.

TITLE_KEY correspond to title-key in ".yml" file.

  PID_KEY correspond to pid-key in ".yml" file.

  ##### Application YAML Based Configuration

```yaml
server:
    port: 7003

  spring:
  active-directory:
    principal: "@smartict.com.tr"
    domain-controllers: dc=smartict,dc=com,dc=tr
    organization-unit: ou=Kullanicilar,
    security-authentication: simple
    filter-query: (objectClass=*)
    provider-url: ldap://192.168.10.50:389
    username-key: sAMAccountName
    name-key: givenName
    surname-key: sn
    mail-key: mail
    mobile-key: telephoneNumber
    address-key: streetAddress
    title-key: title
    pid-key: employeeNumber

active-directory:
  security:
    cors:
        allowed-hosts:
        - /**
```

####  **server** 

 - **port:** Application's serving port.

>   Note: This port must be exact same value on the docker-compose ***<ports>*** 's value.

#### **spring\activedirectory**

- **principal:** Your Active Directory principle which is start with "@" and  continue with example.com . Exactly it is userPrincipalName (Ex username@example.com) without username . 
  - **domain-controllers: **Your Active Directory domain controllers . You have to define like -> dc=example,dc=com
- **organization-unit:** When you are searching users you have to define their organizational units. Be careful about searcher user's permissions, because they need to permission for searching that organizational units. It is up on your Active Directory Polices . Example usage:
    -  if its only one; ou=Hired.
  -  if its more then one ou=Hired,ou=NewHired, . 
    - If you left empty this configuration , it will try to search every organizational units. 
  - left a "," end of last element .
  - **security-authentication:** It is your authentication method . define it as simple if you just want basic username and password authentication.
- **filter-query: **When you are searching users , you can give ldap query for search filtering . Example usage -> "(objectClass=users)" this will return users , which is have "users" value in their objectClass attribute. check this link for example queries -> https://ldapwiki.com/wiki/LDAP%20Query%20Examples
- **provider-url:** it is your active directory url
  - If you have "ssl" feature in your Active Directory , you can give it like : ldaps://yourADip:yourLdapsPort (default port is always 636 if someone not changed it) .
  -  If you don't have "ssl" you can define it like -> ldap://yourADip:yourLdapPort (default port is always 389 if someone not changed it).
- **username-key:**  When users are trying to authenticate with their principle and passwords , The service will return their attributes .Service need to search them with unique attribute for returning their . So it's better to use "sAMAccountName" attribute because its a unique attribute and best thing for search unique user.  
- **name-key:**  Define the attribute id that you are using for user's real names like; "Jhon" ,"Alex" ,"Tony" . Using "givenName" attribute id will be better.
- **mail-key:** This key is ; what attribute id  you are using for user's mail.
- **mobile-key:** This key is ; what attribute id you are using for user's phone numbers . It can be mobile or home number.
- **address-key:** This key is; what attribute id you are using for user's address . Using "streetAddress" attribute will be better.
- **title-key:** This key is;what attribute id you are using for user's Job title . Like ; "Manager","Instructor","Security". Using "title" attribute will be better.
- **pid-key:** This key is;what attribute id you are using for user's employee numbers.

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

**POST  activedirectory/authenticate**

This endpoint will authenticate user with username and password from Active Directory.

```json
{
	"username":"username",
	"password":"password"
}
```

**POST  activedirectory/users**

This endpoint will send all active directory users, you can achieve from Active Directory.

```json
  {
  	"username":"username",
  	"password":"password"
  }
```

**RESPONSE**

For Response , service is using  ActiveDirectoryUser Class for user data in RestResponse Class (ResponseEntity<RestResponse<ActiveDirectoryUser>>) . So you should catch the response with RestResponse Class .

Response of "getAllUser" is list of ActiveDirectoryUser in RestResponse Class's  data T. Actualy it is almost same with "/authentication". The different thing is ; RestResponse data type . Data T is  , "List<ActiveDirectoryUser>" . 

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
