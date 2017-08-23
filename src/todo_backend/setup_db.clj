(ns todo-backend.setup-db
  (:require [clojure.java.jdbc :refer :all :as sql]))

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2:file"
              :subname "resources/db/todos;DB_CLOSE_DELAY=-1"})

(defn create-todos-table []
  (sql/db-do-commands db-spec
                      (sql/create-table-ddl :todos
                                            [[:id "bigint" :primary :key "auto_increment"]
                                             [:title "varchar"]
                                             [:completed "boolean"]
                                             [:position "int"]])))

(defn populate-single-todo []
  (sql/insert! db-spec :todos {:title "Get milk" :completed false}))