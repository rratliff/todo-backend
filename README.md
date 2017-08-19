# todo-backend

Implementation of TODO in Clojure. Implements the TODO Backend API (http://www.todobackend.com/index.html).

Middleware was stolen from https://github.com/darrenhaken/todo-backend-clojure

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## DB setup

```
lein compile
lein repl
```

In the repl:

```
(use 'todo-backend.setup-db)
(create-todos-table)
(populate-single-todo)
```