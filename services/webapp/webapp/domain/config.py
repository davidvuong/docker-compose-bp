# -*- coding: utf-8 -*-


class ServerConfig(object):
    def __init__(self, host, port):
        self.host = host
        self.port = port


class ApiConfig(object):
    def __init__(self, endpoint):
        self.endpoint = endpoint
