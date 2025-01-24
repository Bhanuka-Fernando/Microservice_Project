# Microservice_Project

This project is a microservices architecture with three core modules: Order, Product, and Inventory.

Workflow:

When an order is placed, the Inventory Module checks item availability.
The Product Module validates if the item is listed for sale.
If both checks pass, the order is processed in the Order Module.
Key Features:

Eureka Service Discovery: Ensures smooth communication between services, supporting both sync and async methods.
Keycloak Security: Secures endpoints with role-based access and token authentication.
This system is secure, modular, and designed for efficient order processing.

start zookeeper = bin/zookeeper-server-start.sh config/zookeeper.properties
start kafka server = bin/kafka-server-start.sh config/server.properties
start keycloak = bin/kc.sh start-dev

