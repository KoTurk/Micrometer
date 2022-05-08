# Workshop (Micrometer)

## Prerequisites
- IDE
- Maven (Optional)
- Prometheus
- Grafana

**Please ensure you have prepared your machine well in advance of the workshop. Your time during the workshop is valuable, and we want to use it for learning, rather than setting up machines.**

## Preparing your machine for the workshop
- [Install the pre-requisites](#install-the-pre-requisites)
- [Get a copy of this repository](#get-a-copy-of-this-repository)
- [Exercises](#exercises)

### Install the pre-requisites
- Install an IDE like https://www.jetbrains.com/idea/download/
- (Optional) Download Maven https://maven.apache.org/download.cgi and install https://maven.apache.org/install.html
- Download and start Prometheus, see https://prometheus.io/docs/introduction/first_steps/
- Download and start Grafana, see https://grafana.com/grafana/download

### Get a copy of this repository
Clone or download this repo.

### Exercises
1. add the dependency spring-boot-starter-actuator to the Maven pom.
2. build the project with <code>mvn package</code> or from project <code>mvnw package</code>
3. To enable your prometheus / metrics endpoint add the following to your application.yml

<code>
management.metrics.export.prometheus.enabled: true<br>
management.endpoints.web.exposure.include: 'metrics, prometheus'
</code>

3. Within the src of the project there are three packages:
- _01_timers
- _02_counters
- _03_gauges

Each of these packages contains some assignments.
Begin with the first (01) class and read the TODO and comments.
