# -*- coding: utf-8 -*-


class AwsAuthentication:
    def __init__(self, access_key: str, secret_key: str) -> None:
        self.access_key: str = access_key
        self.secret_key: str = secret_key


class SqsConfig:
    def __init__(self, endpoint_url: str, port: int, region: str, use_ssl: bool = False) -> None:
        self.endpoint_url = endpoint_url
        self.port = port
        self.region = region
        self.use_ssl = use_ssl
