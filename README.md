# Dockerized Spring Boot Messaging Application

This is a spring boot application for sending messages from one user to another user using REST APIs and Docker containers with PostGresSQL.

Prerequisite: 
#####Docker must be installed in your system. [Download Docker here](https://docs.docker.com/get-docker/).

Now gradle sync or rebuild application to download all the dependencies

To run the application, either clone or fork the develop branch of repository to your local directory and then execute the following command(s) in the terminal in same order from the Spring Boot Application's root directory:

`$ docker compose build`

`$ docker compose up`

Note: If you ever need the images in Docker to be removed or cleaned up then only try the following command:

`$ docker compose down`

All the necessary images will be created and uploaded to the Docker and then the Spring Boot Application will start running on Docker container.

Once the application is up and running, use **Postman** to test the following APIs.

### User API:

#### POST Request:
Create user:
[http://localhost:8080/messaging/users](http://localhost:8080/messaging/users)

Body:

    {
        "name": "Homie555"
    }

#### GET Requests:
Get all users:
[http://localhost:8080/messaging/users](http://localhost:8080/messaging/users)

Get user by id:
[http://localhost:8080/messaging/users/111](http://localhost:8080/messaging/users/111)

#### DELETE Request:
Delete user:
[http://localhost:8080/messaging/users/222](http://localhost:8080/messaging/users/222)

### Message API:

#### POST Request:
Post or send message:
[http://localhost:8080/messaging/messages/send](http://localhost:8080/messaging/messages/send)

Add the following parameter in the **request header**:

KEY: `user_id`

VALUE: 111 (Type of the value should be **Long**)

Body:

    {
        "receiverId" : 222,
        "message" : "Hey Homie! what's up?"
    }

#### GET Requests:
Get all messages:
[http://localhost:8080/messaging/messages](http://localhost:8080/messaging/messages)

Get message by id:
[http://localhost:8080/messaging/messages/1](http://localhost:8080/messaging/messages/1)

##### The request header `user_id` has to be added to the following APIs:

Get all sent messages:
[http://localhost:8080/messaging/messages/sent](http://localhost:8080/messaging/messages/sent)

Get all received messages:
[http://localhost:8080/messaging/messages/received](http://localhost:8080/messaging/messages/received)

Get all messages received from a particular user:
[http://localhost:8080/messaging/messages/sender/2](http://localhost:8080/messaging/messages/sender/2)

