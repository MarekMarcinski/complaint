# Complaint Management Service - Empik

## Overview

This project is a RESTful service designed for managing product complaints, developed as part of a backend recruitment task.
It allows users to add new complaints, edit the content of existing complaints, and retrieve previously saved complaints.

The service implements the **Command Query Responsibility Segregation (CQRS)** pattern and is split into two distinct microservices:

1.  **`complaint-command`**: Handles write operations (creating/editing complaints) and stores events.
2.  **`complaint-query`**: Handles read operations (retrieving complaints) and serves data optimized for querying.

## Live Demo

A live demo of this application is available at:

**Base URL:** `https://complaint.marcinski.ovh/`

**How it works:**
A reverse proxy is configured at the base URL. It routes incoming HTTP requests to the appropriate backend microservice based on the HTTP method used:
* **`POST` / `PUT` requests** (writes) are automatically forwarded to the `complaint-command` service.
* **`GET` requests** (reads) are automatically forwarded to the `complaint-query` service.

**Example API Calls via Demo URL:**
* Create a complaint: `POST http://complaint.marcinski.ovh/api/v1/complaints` (with request body)
* Edit a complaint: `PUT http://complaint.marcinski.ovh/api/v1/complaints/{id}` (with request body)
* Get all complaints: `GET http://complaint.marcinski.ovh/api/v1/complaints`
* Get a specific complaint: `GET http://complaint.marcinski.ovh/api/v1/complaints/{id}`

*For sample request bodies required for the `POST` and `PUT` operations, please see the [Microservice Details](#microservice-details) section below.*


## Core Features

* **Add New Complaints:** Submit new product complaints.
* **Edit Complaint Content:** Modify the description/content of an existing complaint.
* **Retrieve Complaints:** Fetch a list of complaints (with pagination) or a single complaint by its ID.
* **IP-Based Country Detection:** Automatically determines and stores the country of the reporter based on their IP address using an external  service.
* **Uniqueness Constraint:** Ensures complaints are unique based on the combination of `productId` and `reporterName`.
* **Duplicate Handling:** If an attempt is made to add a complaint with an existing `productId` and `reporterName` combination, instead of creating a duplicate, the submission `counter` for the existing complaint is incremented.

## Architecture: CQRS

The application follows the CQRS pattern:

* **Command Side (`complaint-command`):** Receives commands (`CreateComplaintCommand`, `EditComplaintCommand`) and persists changes as events. It uses **MongoDB** for event storage.
* **Event Bus (`Apache Kafka`):** Acts as the intermediary, ensuring durable and asynchronous communication between the services. Events published by the command side are sent to Kafka topics.
* **Query Side (`complaint-query`):** **Subscribes to the relevant Kafka topics**. It consumes the domain events published by the command service. Upon receiving an event, it updates its read-optimized data store (**MySQL**) accordingly. It then serves read requests based on this optimized data model.

## Technology Stack (Assumed/Required)

* **Language:** Java 21
* **Framework:** Spring Boot
* **Build Tool:** Maven
* **Databases:**
    * MongoDB (for `complaint-command` event store)
    * MySQL (for `complaint-query` read model) with Liquibase to manage DB changes.
* **Messaging:** **Apache Kafka** (as the Event Bus)
* **Architecture:** DDD, CQRS, Event Sourcing (potentially on command side), Microservices

## Microservice Details

### 1. `complaint-command`

* **Purpose:** Handles write operations (Create, Update), enforces business rules, and **publishes events to Kafka**.
* **Default Port:** `2138`
* **Database:** MongoDB
* **Event Publishing:** Publishes events (`ComplaintCreatedEvent`, `ComplaintContentEditedEvent`) to Kafka topics upon successful command execution.
* **API Endpoints:**
    * **`POST /api/v1/complaints`**: Creates a new complaint or increments the counter for duplicates, then publishes an event.
        * **Request Body:** `CreateComplaintRequest`
            ```json
            {
              "reporterName": "John Doe",
              "contents": "The product arrived damaged.",
              "complaintProductId": "f47ac10b-58cc-4372-a567-0e02b2c3d479"
            }
            ```
        * **Validations:**
            * `reporterName`: Must not be blank.
            * `contents`: Must not be blank.
            * `complaintProductId`: Must not be null, must be a valid UUID format.
    * **`PUT /api/v1/complaints/{id}`**: Edits the content of an existing complaint, then publishes an event.
        * **Path Variable:** `id` (The unique identifier of the complaint to edit)
        * **Request Body:** `EditComplaintRequest`
            ```json
            {
              "contents": "Updated complaint: The packaging was torn, and the product has scratches."
            }
            ```
        * **Validations:**
            * `contents`: Must not be blank.

### 2. `complaint-query`

* **Purpose:** Handles read operations (Retrieve), **consumes events from Kafka** to create/update its read model.
* **Default Port:** `2137`
* **Database:** MySQL
* **Event Consumption:** Subscribes to Kafka topics, listens for events (`ComplaintCreatedEvent`, `ComplaintContentEditedEvent`), and updates its MySQL database accordingly.
* **API Endpoints:**
    * **`GET /api/v1/complaints`**: Retrieves a paginated list of complaints from its MySQL read model.
        * **Query Parameters:** Supports standard Spring Data `Pageable` parameters (e.g., `?page=0&size=10&sort=creationDate,desc`).
        * **Response:** A pageable list of complaint objects.
    * **`GET /api/v1/complaints/{id}`**: Retrieves a single complaint by its unique identifier from its MySQL read model.
        * **Path Variable:** `id` (The unique identifier of the complaint)
        * **Response:** A single complaint object or a 404 Not Found error.

## Setup and Running

1.  **Prerequisites:**
    * JDK 21
    * Maven
    * Kafka
    * MongoDb
    * MySQL
2.  **Run:**
    * There is two way to run, faster and slower
    * Faster:
        * Use prepered docker-compose file fallowing: `cd local-setup` `docker-compose up`. This will setup all neccessary services (MongoDB, Kafka, MongoDB) and run both complaint-command and complaint-query as a docker container.
    * Slower:
        * Ensure Kafka, MongoDB, and MySQL instances are running and accessible. (You can use local-setup/docker-compose-infrastructure.yml)
        * Build both services using `cd service_name` and `mvn clean install`
        * Execute the generated JAR with command: `java -jar target/service_name-*.jar` for both services.

## Future Improvements

* **Implement Dead Letter Queue (DLQ) for Event Consumption:** Enhance error handling in the `complaint-query` service's Kafka consumer. When an event cannot be processed successfully after configured retries (due to transient issues like database unavailability, data format problems, or unexpected exceptions), the problematic message should be automatically routed to a designated Dead Letter Queue (DLQ) topic in Kafka. This prevents blocking the processing of subsequent messages in the main topic and allows for later analysis, manual intervention, or automated reprocessing of these failed messages.
* **Implement Application Metrics:** Integrate comprehensive monitoring and metrics collection to gain visibility into the services' operational health and performance. Utilize libraries like **Micrometer** with Spring Boot Actuator to expose key metrics. This should include:
    * **Technical Metrics:** JVM statistics (heap usage, GC activity, threads), system metrics (CPU, memory), API endpoint metrics (request latency, throughput, error rates), database connection pool usage, Kafka producer/consumer metrics (e.g., publish rate, consumer lag, processing time).
    * **Business Metrics:** Rate of new complaint creation, number of complaints processed per time unit, count of duplicate increments, GeoIP lookup success/failure rates.
    * Expose these metrics via a standard endpoint (e.g., `/actuator/prometheus`) suitable for scraping by monitoring systems like Prometheus and visualization in dashboards (e.g., Grafana). Set up alerting based on critical metrics. 
* **Enhance Testing Strategy:** Improve the breadth and depth of automated testing to increase confidence in the system's correctness and robustness.