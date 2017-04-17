# -*- coding: utf-8 -*-
from __future__ import absolute_import

import logging
import json
import httplib

from functools import partial

from flask import Flask
from flask import render_template
from flask import request


def index(config):
    server_config = config.server_config
    api_endpoint = 'http://%s:%s' % (server_config.host, server_config.port)
    return render_template('index.tpl.html', api_endpoint=api_endpoint)


def send_message(config):
    try:
        request_payload = json.loads(request.data)
    except (ValueError, TypeError) as e:
        return 'invalid json request payload', httplib.BAD_REQUEST

    message = request_payload.get('message')
    if message is None:
        return 'message missing from request payload', httplib.BAD_REQUEST

    logging.info('received message from client, message=%s', message)
    return '', httplib.OK


def init(config):
    app = Flask('webapp')

    app.add_url_rule('/', 'index', partial(index, config), methods=['GET'])
    app.add_url_rule(
        '/send-message',
        'send_message',
        partial(send_message, config),
        methods=['POST']
    )
    return app
