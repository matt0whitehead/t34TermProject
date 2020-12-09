# Description
The goal of our project is to become more experienced with containers and development environments. Within our project scope we will be utilizing containerization to receive, manipulate, and output information related to coronavirus cases. This will allow us to not only become more skilled in working with containers, but help us alleviate a multitude of development and compatibility issues. Currently constructed is a RESTFUL web server that is able to receive an HTTP request and log the request body to STDOUT. Also, a Dockerfile with kubernetes that when built builds a Docker image to provide an API and a backend. The API is written in Java with spark.


# Usage
Build the webserver by navigating to /api then by running `mvn package`. This requires that maven is installed on your system.

Run the webserver with `java -jar target/t34TermProject-1.0-SNAPSHOT.jar` when in /api.

# Docker
## API
Build the API docker image by navigating to /api then by running `docker build -t n8snyder/370api .`.

Push to docker hub with `docker push n8snyder/370api`.

Run with `docker run -p 8080:8080 n8snyder/370api`. This will run the webserver from the local docker image and will download the image first if it does not exist.

To update your local image, run `docker pull n8snyder/370api`.
## Database
Build the database docker image by navigating to /database then by running `docker build -t n8snyder/370db .`.

Push to docker hub with `docker push n8snyder/370db`.

Run with `docker run n8snyder/370db&`. This will run the database from the local docker image and will download the image first if it does not exist.

To update your local image, run `docker pull n8snyder/370db`.
