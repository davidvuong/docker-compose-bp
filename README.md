# docker-compose-bp

**Welcome to docker-compose-bp!**

This repository is meant to be an example and somewhat boilerplate that aims to demonstrate how to configure and run your docker containers locally via [docker-compose](https://docs.docker.com/compose/).

The purpose of this project is to provide a development workflow that allows you to take advantage of some of the nicer properties of Docker. I want to show how to build/compile and install dependencies on the same environment across develop, staging, production, and on an individual developer's local machine.

There are a few requirements:

1. Development feedback cycle must be fast (auto restart/compile on change/save)
1. A variety of languages used for each service
1. Data persistence layer (also as a container)
1. Communication between services via an in-memory queue

## Installation

Install docker and core dependencies:

```bash
brew install docker-compose
brew install cask
brew cask install docker
brew cask install docker-toolbox
```

Alias the docker-compose command (optional):

```bash
alias dc=$(which docker-compose)
```

## Running the project

Start all services in our cluster:

```bash
docker-compose up -d --force-recreate --build
```

Create database and run migrations:

```bash
docker-compose run --rm db createdb webapp -h db -U postgres
docker-compose run --rm db createdb api -h db -U postgres

# Only necessary if first time building
docker build --tag davidvuong/flyway:local flyway/

# Every subsequent new database migration
bash ./scripts/migrate_db $(./scripts/get_host_ip.py) api
bash ./scripts/migrate_db $(./scripts/get_host_ip.py) webapp

# Inspect your database via psql
docker-compose run --rm db psql -h db -U postgres
```

Open the application:

```bash
open http://localhost:5000
```

Remove all traces of our cluster (excluding images):

```
docker-compose down
```

## Project structure and flow

There are 6 separate services used in this demonstration. Each service uses a different language (python, scala, ruby, js, rust, go), all communicating either via HTTP or messages through SQS. There are 2 HTTP servers (WebSockets + API) and 4 SQS workers. You can see the source in `/services` for more details.

The flow is simple. A user requests for the app via a web browser. They enter some text, click the send button and a request is made to the API. The API creates a record in Postgres and forwards the user input as a message to a SQS worker downstream. It's then transformed by 4 separate SQS workers (each with a queue inbetween them). At each step, a request is made back to the API, updating the original record with each intermediate transformation.

When the final SQS worker completes, the overall transformation is considered complete. For usability, the client also establishes and maintains a socket connection with the HTTP+Websocket server. Each time the API receives an update from a worker, the same information is also forwarded to the client, allowing the webapp show progresssion in realtime.

## Debugging SQS queues

You can inspect SQS queues during runtime via boto3 (on your host):

```bash
pip install boto3
```

Connect and ping the queue you want to inspect:

```python
import json
import boto3

# @see: http://boto3.readthedocs.io/en/latest/reference/services/sqs.html#client
client = boto3.client('sqs',
    endpoint_url='http://localhost:9324',
    region_name='elasticmq',
    aws_secret_access_key='',
    aws_access_key_id='',
    use_ssl=False
)

# @note: list all queues, purge a queue, and send a message to a queue:
sqs_queues = client.list_queues()

queue_url = sqs_queues['QueueUrls'][0]
client.purge_queue(QueueUrl=queue_url)
client.send_message(QueueUrl=queue_url, MessageBody=json.dumps({'data': 'my message body'}))
```

## Useful Docker/Docker Compose commands

[Here](./docs/docker-compose-cheatsheet.md) are some of a few useful commands I found myself using a lot during the development of this project.
