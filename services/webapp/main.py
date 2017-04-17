#!/usr/bin/env python
# -*- coding: utf-8 -*-
import logging

from webapp.config import load as load_config
from webapp.server import init as init_app


if __name__ == '__main__':
    logging.basicConfig(level=logging.DEBUG)

    config = load_config()
    app = init_app(config)
    app.run(
        host=config.server_config.host,
        port=config.server_config.port,
        debug=config.environment != 'production'
    )
