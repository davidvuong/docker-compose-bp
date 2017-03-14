FROM imageintelligence/scala:latest
MAINTAINER David Vuong <david.vuong256@gmail.com>

COPY . /root/app
WORKDIR /root/app

RUN sbt compile
CMD sbt "run"
