(ns todo-backend.setup-db
  (:require [clojure.java.jdbc :refer :all :as sql]))

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2:file"
              :subname "resources/db/documents;DB_CLOSE_DELAY=-1"})

(defn create-documents-table []
  (sql/db-do-commands db-spec
                      (sql/create-table-ddl :documents
                                            [[:id "bigint" :primary :key "auto_increment"]
                                             [:name "varchar"]
                                             [:body "varchar"]])))

(defn populate-single-doc []
  (sql/insert! db-spec :documents {:name "Bicycle" :body "Hello world"}))