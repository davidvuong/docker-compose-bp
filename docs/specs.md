# API Specifications

## WebApp

```
SocketIO (connect) /socket/connect
SocketIO (server->client) {
  "originalMessage": "plain_text",
  "transformedMessage": "transformed plain_text",
  "status": "IN_PROGRESS|COMPLETED|ERROR"
}
```

```
POST /api/send-message {
  "message": "plain_text",
  "clientId": "uuid"
}
Response {
  "id": "uuid"
}
```

```
POST /webhook/receve-message {
  "originalMessage": "plain_text",
  "transformedMessage": "transformed plain_text",
  "correlationId": "uuid",
  "status": "IN_PROGRESS|COMPLETED|ERROR"
}
```

```
POST /api/clients {}
Response {
  "id": "uuid"
}
```

## API

```
POST /messages {
  "message": "plain_text",
  "correlationId": "uuid",
  "webhookUrl": "httpUrl"
}
Response {
  "id": "uuid",
  "createdAt": "unix_epoch"
}
```

```
PUT /messages/:id {
  "transformedMessage": "transformed plain_text",
  "status": "IN_PROGRESS|COMPLETED|ERROR"
}
Response {}
```

## Workers: PySorter, ...

```
IngressMessage {
  "message": "plain_text"
}

EgressMessage (IngressMessage to next worker) {
  "message": "plain_text"
}
```
