FROM python:2.7.13-alpine
MAINTAINER David Vuong <david.vuong256@gmail.com>

COPY . /root/app

ENV PYTHONPATH /root/app/site-packages:$PYTHONPATH

WORKDIR /root/app
RUN pip install -r requirements.txt \
  --target=/root/app/site-packages \
  --find-links=/root/app/pip-cache \
  --upgrade
CMD python webapp/server.py
