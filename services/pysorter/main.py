#!/usr/bin/env python
# -*- coding: utf-8 -*-
from pysorter.config import load


def main():
    config = load()
    print(config.aws_auth.__dict__)
    print(config.sqs_config.__dict__)

if __name__ == '__main__':
    main()
