# -*- coding: utf-8 -*-


class ServerConfig(object):
    def __init__(self, host, port, host_ip):
        self.host = host
        self.port = port
        self.host_ip = host_ip


class ApiConfig(object):
    def __init__(self, endpoint):
        self.endpoint = endpoint
