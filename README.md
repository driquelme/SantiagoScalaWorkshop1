
# Cats Workshop - Santiago Scala


## Server params

port 8080

services:

    GET /comments
    POST /comments


## Available sbt commands

sbt it:test

sbt scalastyle

sbt scalafmt

## Sample Requests:

`curl -d '{"id":"0", "text":"Hola", "timestamp":"100"}' -H "Content-Type: application/json" -X POST http://localhost:8080/comments`

`curl http://localhost:8080/comments` 
