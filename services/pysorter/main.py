#!/usr/bin/env python
# -*- coding: utf-8 -*-
import logging

from pysorter.config import load
from pysorter.domain.messaging import IngressMessage, EgressMessage
from pysorter.vendor import sqs


def message_handler(message: IngressMessage) -> EgressMessage:
    content = ' '.join(sorted(message.content.split(' ')))
    return EgressMessage(message.id, message.content, content, message.created_at)


def main():
    logging.basicConfig(level=logging.INFO)
    logging.getLogger('botocore').setLevel(logging.CRITICAL)

    config = load()

    logging.info('pysorter worker started, polling on ingress queue')
    sqs_client = sqs.connect(config.sqs_config, config.aws_auth)
    while True:
        logging.debug('initiating long poll receive_message call')
        message = sqs.receive_message(sqs_client, config.sqs_config.sqs_ingress_queue_url)
        if message is None:
            logging.debug('no messages found in queue... trying again')
            continue

        logging.info('received message from ingress, messageId="%s"', message.id)
        sqs.send_message(
            sqs_client,
            config.sqs_config.sqs_egress_queue_url,
            message_handler(message)
        )
        logging.info('sent new message to egress, messageId="%s"', message.id)

if __name__ == '__main__':
    main()
