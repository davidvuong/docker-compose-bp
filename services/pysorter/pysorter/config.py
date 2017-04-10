# -*- coding: utf-8 -*-
import os
from typing import Dict, Union

from .domain.aws import AwsAuthentication, SqsConfig

__all__ = ['Config', 'load']


class Config:
    def __init__(self, aws_auth: AwsAuthentication, sqs_config: SqsConfig) -> None:
        self.aws_auth = aws_auth
        self.sqs_config = sqs_config


def _load_unsafe() -> Dict[str, Union[str, bool]]:
    return {
        'aws_access_key': os.environ['AWS_ACCESS_KEY'],
        'aws_secret_key': os.environ['AWS_SECRET_KEY'],
        'sqs_endpoint_url': os.environ['SQS_ENDPOINT_URL'],
        'sqs_port': int(os.environ['SQS_PORT']),
        'sqs_region': os.environ['SQS_REGION'],
        'sqs_use_ssl': os.environ['SQS_USE_SSL'] == '1',
        'sqs_ingress_queue_url': os.environ['SQS_INGRESS_QUEUE_URL'],
        'sqs_egress_queue_url': os.environ['SQS_EGRESS_QUEUE_URL'],
    }


def load() -> Config:
    env: Dict = _load_unsafe()
    return Config(
        AwsAuthentication(env['aws_access_key'], env['aws_secret_key']),
        SqsConfig(
            env['sqs_endpoint_url'],
            env['sqs_port'],
            env['sqs_region'],
            env['sqs_ingress_queue_url'],
            env['sqs_egress_queue_url'],
            env['sqs_use_ssl']
        )
    )
