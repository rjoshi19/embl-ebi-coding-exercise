#EMBL EBI
##SENIOR JAVA DEVELOPER (FIRE) 
###CODE SUBMISSION

## Pre-requisites
```
1. Maven
2. docker-ce
3. docker-compose
```
## Installation
```
1. Execute command: mvn clean install
2. Start Docker daemon: sudo systemctl start docker
3. Run commands:
    a. docker-compose build
    b. docker-compose up
```
## How to Test the API

The API has been integrated with Swagger UI for testing and documentation.
Use the links below to access them:

[API](http://localhost:9090/swagger-ui/ "API@Swagger UI")

[API Docs JSON](http://localhost:9090/v2/api-docs "API Docs")

###Authentication
JWT  Auth has been used to secure the API. A token has to be generated first using the below endpoint and then added as a Header to the requests.

[Auth API](http://localhost:9090/api/v1/authenticate)

####Credentials for Auth Body:
```
Username: testuser
Password: password
``` 
Please note that the username and password are mentioned here for convenience. The secret, username and password should be
stored in a secure place such as [Hashicorp's Vault](https://www.vaultproject.io/).

###API

API are listed under Persons tag on [Swagger UI](http://localhost:9090/swagger-ui/ "API@Swagger UI"). In order to use them, 
they must be authenticated first using the [Auth API](http://localhost:9090/api/v1/authenticate) mentioned earlier.

If the API is being tested using Postman or SOAP UI, the token generated by the Auth API must be passed in the header of 
the API being invoked. 
```
Header Key: Authorization
Header Value: Bearer <Token from Auth API Response>
```

If the API is being tested using Swagger UI, then click on the Authorize button on the top right side of the screen. Enter the
value as Bearer Token and click on Authorize. This will enable auth on all the API and they can
be tested using Try it out buttons available at each endpoint.

The details of request body, response body and response types are available on Swagger UI for convenience.

##Requirements of the Exercise

###Testing
* Tests written with JUnit can be used to perform unit testing. PowerMockito is the way to go.
* Performance Tests should be executed based on the estimated traffic expected. 
* Contract testing must be integrated to ensure contracts with consumers are not impacted after any change. This can be achieved using [Pact Contract Testing](https://docs.pact.io/).

###Security
* JWT Auth is implemented to add security to the API.
* Penetration Testing must be performed to ensure the API are secure enough. We should take care of CSRF, SQL Injection, DDoS attacks and backdoors.
* CORS must be carefully implemented or avoided if possible.
* Infrastructure security measures such as firewalls and use of reverse proxy is encouraged.

###Scalability

* Nginx has been implemented which acts a load balancer.
* Use of Docker helps in easy horizontal scaling.
* The application can be deployed to cloud or PaaS such as PCF to achieve easier vertical scaling.

###Limitations
* The application uses only 1 server even though a load balancer is implemented. This is because an in-memory instance of H2 database is used.
Each server will have its own H2 Database which breaks the API. This can be fixed by having the database outside in a seperate container.
* An ID is autogenerated for each Person object for unique identification. The JSON body can be modified to include one or some other composite criteria can be used.
* Unit Test MUST be present. The API can never be production ready without unit tests.
* Automation testing can be implemented.

###Documentation
Documentation is implemented as per OpenAPI 3 specifications. It can be accessed through [Swagger UI API Docs](http://localhost:9090/v2/api-docs "API Docs").

###Deployment
Deployment can be automated by using CI/CD tools such as [Jenkins](https://www.jenkins.io/) or PaaS such as [OpenShift](https://www.openshift.com/).







