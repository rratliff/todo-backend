(ns todo-backend.repository
  (:require [clojure.java.jdbc :as sql]
            [clojure.set :refer [rename-keys]]))

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2:file"
              :subname "resources/db/documents;DB_CLOSE_DELAY=-1"})

(defn- row->document [row]
  row)

(defn- document->row [document]
  (println document)
  document)

(defn- sql-result->id [result-seq]
  (->
    result-seq
    first
    vals
    first))

(defn get-all []
  (sql/query db-spec
             ["Select * from documents"]
             {:row-fn row->document}))

(defn create-document!
  [document]
  (let [id (sql-result->id
             (sql/insert! db-spec :documents (document->row document)))]
    (merge document {:id id})))