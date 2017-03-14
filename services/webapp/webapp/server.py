#!/usr/bin/env python
# -*- coding: utf-8 -*-
from __future__ import absolute_import

import logging
import json

from flask import Flask
from flask import render_template
from flask import request

from webapp.config import Config

app = Flask('webapp')


@app.route('/')
def index():
    api_endpoint = 'http://%s:%s' % (config.get('host'), config.get('port'))
    return render_template('index.tpl.html', api_endpoint=api_endpoint)


@app.route('/send-message', methods=['POST'])
def send_message():
    try:
        request_payload = json.loads(request.data)
    except (ValueError, TypeError) as e:
        return 'invalid json request payload', 400

    message = request_payload.get('message')
    if message is None:
        return 'message missing from request payload', 400

    logging.info('received message from client, message=%s', message)
    return 'ok', 200


if __name__ == '__main__':
    logging.basicConfig(level=logging.DEBUG)

    config = Config()
    app.run(
        host=config.get('host'),
        port=config.get('port'),
        debug=True if config.get('environment') != 'production' else False
    )
