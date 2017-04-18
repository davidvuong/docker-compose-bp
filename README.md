# docker-compose-bp

**Welcome to docker-compose-bp!**

This repository is meant to be an example and somewhat boilerplate that aims to demonstrate how to configure and run your docker containers locally via [docker-compose](https://docs.docker.com/compose/).

The purpose of this project is to provide a development workflow that allows you to take advantage of some of the nicer properties of Docker. I want to show how to build/compile and install dependencies on the same environment across develop, staging, production, and on an individual developer's local machine.

There are a few requirements:

1. Development feedback cycle must be fast
1. Applications must auto restart/compile when a chance occurs
1. My MacBook cannot blow up when I run this project
1. The project needs to demonstrate using multiple programming languages
1. Data must be persisted to some database (also in a container)
1. Services must communicate between each other via HTTP or queue

## Installation

Install docker and core dependencies:

```bash
brew install docker-compose
brew install cask
brew cask install docker
brew cask install docker-toolbox
```

## Running the project

Start the service cluster via `docker-compose up`:

```bash
docker-compose up --force-recreate --build
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
```

Open the application:

```bash
open http://localhost:5000
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

## Generic Docker/Docker Compose commands

You might find that typing `docker-compose` all the time is a little tedious. I alias `docker-compose`:

```bash
$ alias doc=$(which docker-compose)
```

#### Purge all service containers and rebuild

```bash
docker-compose rm -f
docker-compose up --force-recreate --build
```

#### Purge _all_ Docker artifacts (stop, rm, rmi)

```bash
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker rmi $(docker images -q)
```

#### Run a specific service and its dependencies

```bash
docker-compose run --rm --service-ports --name <service>_run <service>
```

## Service specific Docker commands

#### Installing Python dependencies

```bash
docker run -it --rm -v $(pwd)/services/webapp:/root/app -w /root/app python:2.7.13-alpine pip download -r requirements.txt --dest /root/app/pip-cache
docker run -it --rm -v $(pwd)/services/webapp:/root/app -w /root/app python:2.7.13-alpine pip install -r requirements.txt --target /root/app-site-packages --no-index --find-links /root/app/pip-cache
```

#### Type checking your Python 3 code

```bash
docker-compose run --rm <python-service> mypy --follow-imports=skip .
```

#### PEP8 your Python code

```bash
docker-compose run --rm <python-service> pep8 --ignore=E501,E701 .
```

Both `mypy` and `pep8` are expected to already be installed in the container.

#### Installing Scala dependencies

```bash
docker-compose run --rm <scala-service> sbt "compile"
```

#### Formatting your Go code

```bash
docker-compose run --rm <golang-service> gofmt -l -s -w /go/src
```
