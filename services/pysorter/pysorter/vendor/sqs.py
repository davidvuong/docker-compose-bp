# -*- coding: utf-8 -*-
import json

from typing import Optional, Dict

import boto3

from ..domain.aws import AwsAuthentication, SqsConfig
from ..domain.messaging import EgressMessage, IngressMessage


def connect(config: SqsConfig, auth: AwsAuthentication):
    return boto3.client(
        'sqs',
        endpoint_url=config.endpoint_url,
        region_name=config.region,
        aws_secret_access_key=auth.secret_key,
        aws_access_key_id=auth.access_key,
        use_ssl=config.use_ssl
    )


def send_message(client, queue_url: str, message: EgressMessage) -> Dict:
    return client.send_message(
        QueueUrl=queue_url,
        MessageBody=json.dumps(message.to_dict())
    )


def receive_message(client, queue_url: str) -> Optional[IngressMessage]:
    message: Dict = client.receive_message(
        QueueUrl=queue_url,
        WaitTimeSeconds=10,
        VisibilityTimeout=60,
        MaxNumberOfMessages=10
    )
    if 'Messages' not in message:
        return None

    body: Dict = json.loads(message['Messages'][0]['Body'])  # always a single message.
    return IngressMessage(body['id'], body['content'], created_at=int(body['createdAt']))
