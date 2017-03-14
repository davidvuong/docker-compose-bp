# -*- coding: utf-8 -*-
import os


class Config(object):
    def __init__(self):
        self.env = {}
        self.env['port'] = int(os.environ.get('PORT', 5000))
        self.env['host'] = os.environ.get('HOST', '0.0.0.0')
        self.env['environment'] = os.environ.get('ENVIRONMENT', 'development')
        self.env['http_api_endpoint'] = os.environ['HTTP_API_ENDPOINT']

    def get(self, key):
        return self.env[key]

    def set(self, key, value):
        self.env[key] = value
