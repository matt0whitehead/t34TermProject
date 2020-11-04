# Description
This project provides information related to coronavirus cases. It utilizes docker with kubernetes to provide an API and a backend. 


# Usage
Build the project with `mvn package`. This requires that maven is installed on your system.

Run the webserver with `java -jar target/t34TermProject-1.0-SNAPSHOT.jar`.

# Docker
Build the API docker image with `docker build -t n8snyder/370api .`.

Push to docker hub with `docker push n8snyder/370api`.

Run with `docker run -p 8080:8080 n8snyder/370api`. This will run the webserver from the local docker image and will download the image first if it does not exist.

To update your local image, run `docker pull n8snyder/370api`.
