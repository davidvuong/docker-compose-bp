# docker-compose-sync

**Welcome to docker-compose-sync!**

This repository is an example project with multiple services demonstrating how to setup [docker-compose](https://docs.docker.com/compose/) and [docker-sync](http://docker-sync.io/). There are 6 services, each communicating via SQS and and reads/writes to a PostgreSQL database.

The purpose of this project is to provide a development workflow that allows you to take advantage Docker. I want to show how to build/compile and install dependencies on the same environment across staging, production and an individual developer's local machine.

There are a few requirements:

1. Development feedback cycle must be fast
1. Applications must auto restart/compile when a chance occurs
1. This to be able to run on my Macbook Pro
1. Project needs to demonstrate on multiple programming languages

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

Start the docker-sync container (terminal 1):

```
docker-sync start
```

Start the service containers (terminal 2):

```
docker-compose rm -f
docker-compose up --force-recreate
```

Create the database (terminal 3):

```
docker-compose run --rm db createdb my-db -h db -U postgres
```

Open the application:

```
open http://localhost:8080
```

## Project structure and flow

There are 6 separate services used in this demonstration. Each service uses a different language (python, scala, ruby, js, rust), all communicating either via HTTP or SQS. There are 2 HTTP servers (WebSockets + API) and 4 SQS workers. You can see the source in `/services` for more details.

The flow is really simple. A user requests for the app via a web browser. They enter text and send it to our HTTP server. The HTTP server passes the user input as messages to a SQS worker downstream. The message is transformed by 4 SQS workers (with a queue between each of them). At each step, a HTTP request is made to one of the HTTP servers, updating each intermediate result.

Each worker has a connection to a HTTP API server that connects to a PostgreSQL database. Workers perform a `POST` request to the API upon completing their transformation. When the final worker completes and the overall transformation is complete, the transformation is marked as complete.

For usability, the client establishes a socket connection with one of HTTP servers. When updates are made to the message, a request is made to the client indicating that progress has been made.

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
