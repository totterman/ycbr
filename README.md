# Yacht Club Boat Register

App to support the Finnish Leisure Boat Inspection procedures in yacht clubs

## Getting Started
### Prerequisites

- **Docker** and **Docker Compose** installed on your machine.
- **Git** installed on your machine.
- A Unix-like terminal (Linux, macOS, or Windows Subsystem for Linux). You need **sh** and **sed** at least.
- **Java 25** installed on your machine.

### Building the Application

```
$ sh build.sh
```

### Running the Application

```
$ docker compose -f compose-<HOSTNAME>.yml up -d
```

### Configuration

For the moment: all the zillion  different .env files around

## Using the application

The application is available at 
`http://<HOSTNAME>:7080/ui`

This simple app supports the process of boat inspections at Finnish boat clubs. 
It is based around the user roles of *Boatowner*, *Inspector*, and *Staff*, 
the main entities being *Boat*, *Inspection Event*, and *Inspection*. 
There is a separate OAuth2 authentication provider for maximal security. 
All system parts run in containers.

### Entities
#### Users

Currently, the app is not connected to any user management system. 
Instead, 5 users in different roles are hardcoded in the Authentication Provider.

| Name	                | Username	  | Password	| Role |
|:---------------------|:----------:| :-------: | :----: |
| Stina Skeppare	    |   stina	   | stina	| Boatowner |
| Ronja Rorsman	    |   ronja	   | ronja	| Boatowner |
| Kalle Kanslist	    |   kalle	   | kalle	| Staff |
| Bengt Besiktningsman |   bengt	   | bengt	| Inspector |
| Jenny Gast	        |   jenny	   | jenny	| Guest |

#### Boats

Currently, the app has no connection to any boat register, hence an AI generated boat register is used as substitute. For whatever reason, the AI decided on water jets instead of real boats.

#### Inspection Events

An Inspection Event has a *place* and a *time interval*. 
Staff *creates* and *updates* Inspection Events. 
Inspectors can *register* in them, and Boatowners can *book* inspections for their boats.

#### Inspections

An Inspection is specific for one *boat*. 
The Inspector fills in information about the inspected boat.

### Roles and Permissions

#### Boatowner

Boatowners are allowed to *see* all Boats and Inspection events. 
They can *update* all information of their own boats by selecting EDIT at the Boat register table. 
They can *book* inspections for their own boats by selecting BOOK at the Inspection Event table.

#### Inspector

Inspectors are allowed to *see* all Boats, Inspection Events, and their own Inspections. 
They can *register* as Inspectors at an Inspection event. 
As registered inspectors, they can *select* uninspected Boats from named Inspection events, and *start* a new Inspection. 
Consequently, they can *update* and *complete* the Inspection information.
Inspections are stored for later access.

#### Staff

Staff members are allowed to *see*, *create*, *update*, and *delete* all Boats, Inspection Events, and Inspections.

#### Guest

A Guest can *see* the boat register, but not the boat details.

## Tech stack

Built using the **spring-addons** project by **ch4mpy**, big thanks!

### Relevant Articles:
- [OAuth2 Backend for Frontend With Spring Cloud Gateway](https://www.baeldung.com/spring-cloud-gateway-bff-oauth2)

### Web frontend

Served through a nginx container.
Built using React 19, Material UI, and Tanstack Query, Router, and Form libraries.

Unit tests with Jest and React Testing Library.

End-to-end tests with Playwright.

### Mobile frontend

React native app, not yet public.

### Reverse Proxy

Actually two interchangeable implementations: nginx and Spring Cloud Gateway.

### Backend-for-Frontend server

Spring Cloud Gateway with Java 25 running netty (reactive!) heavily using spring-addons libraries for OAuth2 integration.

### Authentication Provider

Keycloak 24 running in a container. Should be interchangeable with any OAuth2 provider.

### Application server

Spring Boot 3.5.9 application in Java 25 running Tomcat with virtual threads (project loom). Using JDBC to connect to a Postgres database.

### Database

PostgreSQL running in a container.

### Database initialization

Using Liquibase for database migrations planned whenever they start supporting Postgres version 18.

### Metrics and Tracing

Using Micrometer to export metrics to Prometheus and tracing data to Grafana.

## Keycloak http setup

Start the keycloak container.
Then run:

```
$ docker exec -it {containerID} bash
```

In the container bash shell:

```
# cd keycloak/bin
# ./kcadm.sh config credentials --server http://localhost:8080/auth --realm master --user admin --password <ADMIN_PASSWORD>
# ./kcadm.sh update realms/master -s sslRequired=NONE
# ./kcadm.sh update realms/ycbr -s sslRequired=NONE
```


