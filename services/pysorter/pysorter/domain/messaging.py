# -*- coding: utf-8 -*-
from typing import Dict
from datetime import datetime


class IngressMessage:
    def __init__(self, id_: str, content: str, created_at: int) -> None:
        self.id = id_
        self.content = content
        self.created_at = datetime.fromtimestamp(created_at)

    def to_dict(self) -> Dict:
        return {
            'id': self.id,
            'content': self.content,
            'created_at': self.created_at,
        }


class EgressMessage:
    def to_dict(self) -> Dict:
        pass
