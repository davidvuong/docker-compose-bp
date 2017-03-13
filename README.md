# docker-compose-sync

**Welcome to docker-compose-sync!**

This repository is meant to be an example project that demonstrates how to setup [docker-compose](https://docs.docker.com/compose/) and [docker-sync](http://docker-sync.io/), working with multiple "big" NodeJS projects. It also shows how these projects interface with a PostgreSQL server and intercommunicate via multiple mocked SQS queues.

The main goal of this project is show what the development flow would be like using docker-compose to build and run your entire stack locally.

## Installation

```
brew update
brew install docker-compose
brew install cask
brew cask install docker
brew cask install docker-toolbox
```

This project was only tested on a Mac however theoretically it should work on any platform that's supported by Docker.

## Running the project

Start the containers:

```
docker-compose rm -f
docker-compose up --force-recreate
```

Create the database:

```
docker-compose run --rm db createdb my-db -h db -U postgres
```

Open the application:

```
open http://localhost:8080
```

## Project structure

There are 6 separate services in used in this demonstration:

1. A simple service that serves an index.html file (Flask)
1. A HTTP API used by the frontend JavaScript inside the index.html file (Scala HTTP4s)
1. A SQS worker (worker A) to consume messages posted by the HTTP API (Ruby)
1. A SQS worker (worker B) downstream for more message consumption (NodeJS)
1. A SQS worker (worker C) downstream for more message consumption (Rust)
1. A SQS worker (worker D) downstream to store the message into a PostgreSQL database (Python)

## Flow

1. User requests for app from project, index.html is returned
1. User enters text into text field and clicks "send" button
1. App makes request to HTTP API via JavaScript
1. HTTP API stores creates a message using the text and pushes to Queue 1
1. Worker A is polling Queue 1, upon new message, applies transformation and pushes to Queue 2
1. Worker B is polling Queue 2, upon new message, applies transformation and pushes to Queue 3
1. Worker C is polling Queue 3, upon new message, applies transformation and pushes to Queue 4
1. Worker D is polling Queue 4, upon new message, takes the message and stores the message into a PostgreSQL database

Note that as each worker completes their transformation, a request is made to the HTTP API to signal the worker has completed.

## Dockerfile, docker-compose and docker-sync structure

TODO

## Useful Docker commands

Install a dependency within your docker container:

```
# install dependency in Python
$ docker run -it -v $(pwd)/services/webapp:/root/app python:2.7.13-alpine pip install --target=/root/app/site-packages --ignore-installed flask
```
