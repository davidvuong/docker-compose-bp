# docker-compose/docker cheatsheet

Here are a few commands (along with their arguments I found fairly useful) in docker-compose and docker.

> Alias docker-compose to make typing these commands easier

```bash
alias dc=$(which docker-compose)
```

> Start your cluster from complete scratch

```bash
docker-compose up -d --force-recreate --build
```

* `--build` forces a `docker build /path/to/dockerfile` before running your service
* `--force-recreate` will clear docker-compose cache and recreate the container after an image is rebuild (from `--build`)
* `-d` runs all your containers in the background

> Purge (along with cache) **only services defined docker-compose.yml**

```bash
docker-compose down
```

> Purge **absolutely all** containers and images on your system

```bash
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker rmi $(docker images -q)
```

> See running/stopped/paused containers

Stop using `docker stats` and use `ctop`.

> Run a specific service and dependencies (docker-compose `links`)

```bash
docker-compose run --rm --service-ports --name <service_name>_run <service>
```

* `--rm` removes the container after exiting
* `--service-ports` maps all container ports (and it stdin, it seems) to the host
* `--name <service_name>_run` name this temporary container (prefixed with `_run`)
* `<service>` name the service to run

> Installing a dependency to a running container

```bash
docker-compose exec <service> pip install <package>
```

* `exec` command hooks into a running container to run the command
* `<service>` name of service to hook into
* `pip install <package>` an arbitrary command to run inside the container

Note that even if you stop/pause/restart the `<service>`, the next time the container is available, changes made via the arbitrary command is persisted. For example:

```bash
$ docker-compose exec webapp python -c "import flask"
>>> import flask
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
ImportError: No module named flask
$ docker-compose exec webapp pip install flask
$ docker-compose exec webapp python -c "import flask; print flask.__version__"
>> '0.12.1'
$ docker-compose stop webapp
$ docker-compose start webapp
$ docker-compose exec webapp python -c "import flask; print flask.__version__"
>> '0.12.1'
```

As you can see even after restarting `webapp`, `flask` was still available.

> `tail -f` one, many or all service logs

```bash
# Follow multiple services
docker-compose --follow --timestamps <service_1> <service_2>

# Follow a single service
docker-compose --follow <service>

# Show the most recent logs of a single service without following
docker-compose --timestamps <service>

# Follow all service logs
docker-compose --timestamps --follow
```

For example:

```bash
docker-compose logs --follow --timestamps webapp pysorter
Attaching to webapp, pysorter
webapp             | 2017-04-19T14:37:15.496985299Z INFO:werkzeug: * Running on http://0.0.0.0:5000/ (Press CTRL+C to quit)
webapp             | 2017-04-19T14:37:15.499867021Z INFO:werkzeug: * Restarting with stat
webapp             | 2017-04-19T14:37:16.306434704Z WARNING:werkzeug: * Debugger is active!
webapp             | 2017-04-19T14:37:16.320869091Z INFO:werkzeug: * Debugger PIN: 173-493-471
pysorter           | 2017-04-19T14:37:21.690897458Z INFO:root:pysorter worker started, polling on ingress queue
```

> Quickly stop/start containers by pause/unpause

```bash
docker-compose pause
docker-compose unpause
docker-compose pause <service>
```

This behaves the same as `docker pause`. It's useful when you want to quickly stop the execution of one or more services.

> Debugging a service with more than just print statements

```bash
docker-compose stop <service>
docker-compose run --service-ports <service>
```

Sometimes we need a debugger (e.g. Python pdb) but if you add a breakpoint in your code, you won't be able to interact with it. This will stop the background service, start up a new service in the foreground and allowing you to interact with the debugger.

Start the service again in background mode after you're done (note this won't restart services currently running):

```bash
docker-compose up -d
```

```bash
$ docker-compose up -d
Starting sqs
Starting db
Starting http_api
Starting pysorter
Starting webapp

$ docker-compose stop db
Stopping db ... done

$ docker-compose up -d
sqs is up-to-date
Starting db
http_api is up-to-date
pysorter is up-to-date
webapp is up-to-date
```

## Language specific commands

> Type checking your Python 3.6.x+ code

```bash
docker-compose run --rm <python-service> mypy --follow-imports=skip .
```

> Style check your Python 2/3 code via PEP8

```bash
docker-compose run --rm <python-service> pep8 --ignore=E501,E701 .
```

> Install Scala dependencies via sbt

```bash
docker-compose run --rm <scala-service> sbt "compile"
```

> Formatting your go-lang code

```bash
docker-compose run --rm <golang-service> gofmt -l -s -w /go/src
```
