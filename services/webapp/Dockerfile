FROM python:2.7.13-alpine
MAINTAINER David Vuong <david.vuong256@gmail.com>

COPY . /root/app
WORKDIR /root/app

RUN apk update && \
    apk add --update alpine-sdk && \
    apk add postgresql-dev && \
    pip install -r requirements.txt

CMD python main.py
