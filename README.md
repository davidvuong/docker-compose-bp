# docker-compose-sync

**Welcome to docker-compose-sync!**

This repository is meant to be an example project that demonstrates how to setup [docker-compose](https://docs.docker.com/compose/) and [docker-sync](http://docker-sync.io/), working with multiple "big" NodeJS projects. It also shows how these projects interface with a PostgreSQL server and intercommunicate via multiple mocked SQS queues.

The main goal of this project is show what the development flow would be like using docker-compose and docker-sync to build and run your entire stack locally whilst also having nice features such as auto-reloading/restarting/recompiling apps when a chance occurs.

## Installation

Install docker and core dependencies:

```
brew install docker-compose
brew install cask
brew cask install docker
brew cask install docker-toolbox
```

Install docker-sync and related dependencies

```
gem install docker-sync
pip install macfsevents
brew install fswatch
brew install unison
```

This project was only tested on a Mac however theoretically it should work on any platform that's supported by Docker.

## Running the project

Start the docker-sync container:

```
docker-sync start
```

Start the service containers:

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

## Project structure and flow

There are 6 separate services in used in this demonstration:

1. A simple service that serves an index.html file (Flask)
1. A HTTP API used by the frontend JavaScript inside the index.html file (Scala HTTP4s)
1. A SQS worker (worker A) to consume messages posted by the HTTP API (Ruby)
1. A SQS worker (worker B) downstream for more message consumption (NodeJS)
1. A SQS worker (worker C) downstream for more message consumption (Rust)
1. A SQS worker (worker D) downstream to store the message into a PostgreSQL database (Python)

The flow is as follows:

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

### Installing Python dependencies within Docker

```
docker run -it --rm -v $(pwd)/services/webapp:/root/app -w /root/app python:2.7.13-alpine pip download -r requirements.txt --dest /root/app/pip-cache
docker run -it --rm -v $(pwd)/services/webapp:/root/app -w /root/app python:2.7.13-alpine pip install -r requirements.txt --target /root/app-site-packages --no-index --find-links /root/app/pip-cache
```

### Installing Scala dependencies within Docker

```
docker run --rm -v $(pwd)/services/http_api:/root/app -v ~/.ivy2:/root/.ivy2 -v ~/.sbt:/root/.sbt -w /root/app imageintelligence/scala:latest sbt "compile"
```
