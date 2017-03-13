#!/usr/bin/env python
# -*- coding: utf-8 -*-
from __future__ import absolute_import

from flask import Flask
from flask import render_template

from webapp.config import Config

app = Flask('webapp')


@app.route('/')
def hello():
    return render_template('index.tpl.html', api_endpoint=config.get('api_endpoint'))


if __name__ == '__main__':
    config = Config()
    app.run(
        host=config.get('host'),
        port=config.get('port'),
        debug=True if config.get('environment') != 'production' else False
    )
