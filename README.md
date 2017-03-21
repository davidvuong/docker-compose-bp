# docker-compose-sync

**Welcome to docker-compose-sync!**

This repository houses an example project that demonstrates how to setup [docker-compose](https://docs.docker.com/compose/) and [docker-sync](http://docker-sync.io/) in a microservices architecture. There are 6 services, each communicating via SQS and reads/writes to a PostgreSQL database.

The purpose of this project is to provide a development workflow that allows you to take advantage of some of the nicer properties of Docker. I want to show how to build/compile and install dependencies on the same environment across develop, staging, production, and on an individual developer's local machine.

There are a few requirements:

1. Development feedback cycle must be fast
1. Applications must auto restart/compile when a chance occurs
1. My Macbook Pro cannot blow up when I run this project
1. The Project needs to demonstrate using multiple programming languages

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

## Running the project

Start `docker-sync` and service cluster:

```
docker-sync start --daemon
docker-compose rm -f
docker-compose up --force-recreate
```

```
docker-compose run --rm db createdb my-db -h db -U postgres
```

Open the application:

```
open http://localhost:8080
```

## Project structure and flow

There are 6 separate services used in this demonstration. Each service uses a different language (python, scala, ruby, js, rust), all communicating either via HTTP or messages through SQS. There are 2 HTTP servers (WebSockets + API) and 4 SQS workers. You can see the source in `/services` for more details.

The flow is simple. A user requests for the app via a web browser. They enter some text, click the send button and a request is made to the API. The API creates a record in Postgres and forwards the user input as a message to a SQS worker downstream. It's then transformed by 4 separate SQS workers (each with a queue inbetween them). At each step, a request is made back to the API, updating the original record with each intermediate transformation.

When the final SQS worker completes, the overall transformation is considered complete. For usability, the client also establishes and maintains a socket connection with the HTTP+Websocket server. Each time the API receives an update from a worker, the same information is also forwarded to the client, allowing the webapp show progresssion in realtime.

## Docker commands

### Installing Python dependencies within Docker

```
docker run -it --rm -v $(pwd)/services/webapp:/root/app -w /root/app python:2.7.13-alpine pip download -r requirements.txt --dest /root/app/pip-cache
docker run -it --rm -v $(pwd)/services/webapp:/root/app -w /root/app python:2.7.13-alpine pip install -r requirements.txt --target /root/app-site-packages --no-index --find-links /root/app/pip-cache
```

### Installing Scala dependencies within Docker

```
docker run --rm -v $(pwd)/services/http_api:/root/app -v ~/.ivy2:/root/.ivy2 -v ~/.sbt:/root/.sbt -w /root/app imageintelligence/scala:latest sbt "compile"
```
