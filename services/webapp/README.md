# webapp

**Welcome to webapp!**

webapp is a simple HTTP/Web sockets server written in Python. It serves the static JS, CSS, HTML files but also handles API calls and Web Socket connections.

The intention for this service was to serve very basic and old school style JavaScript/CSS/HTML (i.e. no pre-processing) but to also be a HTTP/Web socket server.

## Development

Setup a virtualenv on your host machine:

```bash
$ mkvirtualenv webapp
```

Install the dependencies:

```
$ pip install -r requirements.txt
```

Run the server:

```bash
$ python main.py
```

See `/docker-compose.yml` for more details around environment variables, related services etc.
