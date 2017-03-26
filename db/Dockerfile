FROM openjdk:8

ENV FLYWAY_VERSION 4.1.2

RUN wget -O /flyway.tar.gz https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/${FLYWAY_VERSION}/flyway-commandline-${FLYWAY_VERSION}-linux-x64.tar.gz && \
    tar -xzf /flyway.tar.gz && \
    rm /flyway.tar.gz && \
    mv /flyway-${FLYWAY_VERSION} /flyway && \
    ln -s /flyway/flyway /usr/local/bin/flyway

WORKDIR /flyway
CMD ["flyway", "--help"]"
