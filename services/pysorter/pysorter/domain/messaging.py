# -*- coding: utf-8 -*-
from typing import Dict


class IngressMessage:
    def __init__(self, id_: str, content: str, created_at: int) -> None:
        self.id = id_
        self.content = content
        self.created_at = created_at

    def to_dict(self) -> Dict:
        return {
            'id': self.id,
            'content': self.content,
            'createdAt': self.created_at,
        }


class EgressMessage:
    def __init__(self, id_: str, original_content: str, content: str, created_at: int) -> None:
        self.id = id_
        self.content = content
        self.original_content = original_content
        self.created_at = created_at

    def to_dict(self) -> Dict:
        return {
            'id': self.id,
            'content': self.content,
            'originalContent': self.original_content,
            'createdAt': self.created_at,
        }
