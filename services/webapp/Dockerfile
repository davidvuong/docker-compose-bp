FROM python:2.7.13-alpine
MAINTAINER David Vuong <david.vuong256@gmail.com>

COPY . /root/app
WORKDIR /root/app

ENV APP_SITE_PACKAGES /root/site-packages
ENV APP_PIP_CACHE /root/pip-cache
ENV PYTHONPATH $APP_SITE_PACKAGES:$PYTHONPATH

RUN mkdir $APP_SITE_PACKAGES && mkdir $APP_PIP_CACHE && \
  pip install -r requirements.txt \
  --target=$APP_SITE_PACKAGES \
  --find-links=$APP_PIP_CACHE
CMD python main.py
