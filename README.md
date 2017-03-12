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

## Project structure

There are 6 separate services in used in this demonstration:

1. A simple service that serves an index.html file (Django)
1. A HTTP API used by the frontend JavaScript inside the index.html file (Scala HTTP4s)
1. A SQS worker (worker A) to consume messages posted by the HTTP API (Ruby)
1. A SQS worker (worker B) downstream for more message consumption (NodeJS)
1. A SQS worker (worker C) downstream for more message consumption ()
1. A SQS worker (worker D) downstream to store the message into a PostgreSQL database (Python)

## Flow

TODO

## Dockerfile and docker-compose structure

TODO
