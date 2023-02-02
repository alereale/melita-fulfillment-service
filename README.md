# Melita Assessment - Fulfillment Service
###### _author Alessio Reale_

# How-tos
## _Run the application_
<div style="text-align: justify">
In order to run the application on your local machine, you can proceed in different ways as explained below.
</div>

###### <u>_Prerequisite_</u>
<div style="text-align: justify">
<u>Before you start the application</u>:

1) You need ***Zipkin Server*** up and running. Zipkin can can be cloned from the official repository at https://github.com/openzipkin/zipkin.git. You will find proper instructions in their very detailed README or at https://zipkin.io/. Zipkin can also be excluded from the architecture, but you need to remove its dependency from the services using it.
2) The ***RabbitMQ Server*** must be up and running on your machine.
3) The ***Configuration Server*** and the Discovery Service must be up and running. Clone the projects from the URLs below and run them in the same order. Refer to the branchs indicated in brackets:
</div>

- **```Configuration Server:```** https://github.com/alereale/melita-config-server.git (```dev```)
- **```Discovery Service:```** https://github.com/alereale/melita-discovery.git (```dev```)
- **```API Gateway:```** https://github.com/alereale/melita-api-gw.git (```dev```)

<div style="text-align: justify">

4) Clone and run the ***Order Service*** and the ***Support Service***. You can run one or theother first, order does't matter. The repositories are available at the following URLs:
</div>

- **```Order Service:```** https://github.com/alereale/melita-order-service.git (```dev```)
- **```Support Service:```** https://github.com/alereale/melita-support-service.git (```dev```)

<div style="text-align: justify">

5) Be aware that the Configuration Server uses a ***versioned config repository*** for fetching application properties. You clone from the following URLs:
</div>

- **```Config Repository:```** https://github.com/alereale/melita-config-repo.git (```dev```)

###### <u>_Run from Terminal_</u>
<div style="text-align: justify">
To run the application using a terminal you need to have maven and java on your machine. If so you can just open a terminal inside the folder containing the<em> pom.xml </em>file and run the command:
</div>

- **```mvn clean install```**

>Note: this will download/install all the necessary dependencies on your machine.

Once it's done run the command:
- **```mvn spring-boot:run```**
###### <u>_Run in IntelliJ_</u>
In order to build the application through your IDE, you need to do the following:
- run the command **```mvn clean install```** in the terminal
- **build** the project

>Note: in IntelliJ you could create a configuration with the sequence of both commands.
## _Eureka Dasboard_
Once the application is up and running, verify that's discovered by Eureka by browsing to **[http://localhost:8761](http://localhost:8761 "Eureka Dashboard")**.
<div style="text-align: justify">
By visiting the dashboard it will be possible to inspect the registered instances of the services belonging to this microservices architecture. When no services are up and running it will only be possible to see basic indicators such as status and health indicators.
</div>

## _Zipkin Server_
If you decided to use Zipkin Server the dashboard will be available by browsing to **[http://localhost:9411](http://localhost:9411 "Zipkin Server Dashboard")**.


## _RabbitMQ Server_
Once it's up and running, the RabbitMQ Server will be accessible by browsing to **[http://localhost:15672](http://localhost:15672 "RabbitMQ Server")**.
<div style="text-align: justify">
RabbitMQ Server needs authentication and you need to use the default credentials provided in the application property file stored in the Melita Config Repo. 
</div>

## _Datasource_
I used H2 as inMemory datasource. At boot the application creates Customers, Products and Packages tables and inserts records in them for testing purposes. 
You can add/delete/change dummy data in ```data.xml``` file. 

Once the application is up and running, you will be able to access the database through the console accessible by browsing to **[http://localhost:8847/h2](http://localhost:8847/h2 "h2-console")**.
Credentials to access the console are provided in the application property file stored in the Melita Config Repo.
## _RabbitMQ Server_
<div style="text-align: justify">
RabbitMQ Server needs authentication and you need to use the default credentials provided in the application property file stored in the Melita Config Repo. 
</div>

## _Security_
<div style="text-align: justify">
I used Spring Security Basic Authentication, also for RESTful requests between services, with two in memory users created for testing purposes. Users credentials are provided in the application property file stored in the Melita Config Repo.
</div>

## _Logging_
<div style="text-align: justify">

The application uses Log4j2 and stores rolling files in the folder ```log```. In case you want to change path where the logs are stored, the logfile name, or the rolling file criterias, the file ```log4j2-spring.xml``` can be modified accordingly to your needs.

Logs contain spanId and traceId infos to make debug between microservices easier when it's needed.
</div>

## _Testing_
Unit Testing is next step to be covered as I didn't manage to do it within the time I could spend for this project. My fault, sorry.

A Postman Collection is available for Integration tests.

**Alessio Reale**
