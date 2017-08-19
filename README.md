# todo-backend

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
(create-documents-table)
(populate-single-doc)
```