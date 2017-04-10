# pysorter

**Welcome to pysorter!**

pysorter is a SQS worker written in Python. It's a simple little worker that polls an SQS queue for new messages, sorts the message body, makes a call to an API to signal that its updated and passes it along to another queue.

## Development

Setup a virtualenv on your host machine:

```bash
$  mkvirtualenv -p /usr/local/bin/python3 pysorter
```

*(note: I'm using Python 3.6.1)*

Run the worker queue:

```bash
$ python pysorter/pysorter.py
```

See `/docker-compose.yml` for more details around environment variables, related services etc.
