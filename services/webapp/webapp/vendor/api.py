# -*- coding: utf-8 -*-
import json
import uuid
import urllib2

__all__ = ['transform_message']


def transform_message(api_endpoint, webhook_url, message):
    try:
        response = urllib2.urlopen(api_endpoint + '/message', json.dumps({
            'message': message,
            'clientId': str(uuid.uuid4()),
            'webhookUrl': webhook_url,
        }))
    except urllib2.HTTPError as e:
        response = e
    return response
