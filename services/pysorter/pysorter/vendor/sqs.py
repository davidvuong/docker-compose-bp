# -*- coding: utf-8 -*-
import json

from typing import Callable, Union, Dict

import boto3  # type: ignore

from ..domain.aws import AwsAuthentication, SqsConfig
from ..domain.messaging import EgressMessage

SendMessageHandler = Callable[[Union[Exception, Dict]], None]


def connect(config: SqsConfig, auth: AwsAuthentication):
    return boto3.resource(
        'sqs',
        endpoint_url=config.endpoint_url,
        region_name=config.region,
        aws_secret_access_key=auth.secret_key,
        aws_access_key_id=auth.access_key,
        use_ssl=config.use_ssl
    )


def send_message(client, queue_url: str, message: EgressMessage) -> None:
    return client.send_message(
        QueueUrl=queue_url,
        MessageBody=json.dumps(message.to_dict())
    )


def receive_message(client, queue_url: str, handler: SendMessageHandler) -> None:
    while True:
        try:
            response = client.receive_message(
                QueueUrl=queue_url,
                WaitTimeSeconds=10,
                VisibilityTimeout=60,
                MaxNumberOfMessages=10
            )
        except Exception as exception:
            response = exception
        handler(response)
