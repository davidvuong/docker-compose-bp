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

## Running the project

```
???
```

## Project setup

We have 3 NodeJS projects. I've artificially inflated some projects with an unnecessarily large amount of dependencies to test how well big projects (i.e. projects that take up a large amount of disk space) perform. There's also a single PostgreSQL instance running 9.6.1 as well as 3 SQS mock queues (via ElasticMQ).

The flow is quite simple. We have a single HTTP server with a single endpoint named `/hit`. It accepts POST requests in the form:

```json
{
  "data": "any random string you'd like to include here"
}
```

1. HTTP server receives a request, applies basic transformation, pushes to SQS queue
1. Workers poll the SQS queue for messages, upon receiving a new message, applies more transformation, pushes to another SQS queue
1. More workers pull from the 2nd SQS queue, applies even more transformation and adds the record into a PostgreSQL database
