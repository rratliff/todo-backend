(ns todo-backend.repository
  (:require [clojure.java.jdbc :as sql]
            [clojure.set :refer [rename-keys]]))

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2:file"
              :subname "resources/db/documents;DB_CLOSE_DELAY=-1"})

(defn- row->todo [row]
  row)

(defn- todo->row [todo]
  (println todo)
  todo)

(defn- sql-result->id [result-seq]
  (->
    result-seq
    first
    vals
    first))

(defn get-all []
  (sql/query db-spec
             ["Select * from todos"]
             {:row-fn row->todo}))

(defn get-by-id [id]
  (first (sql/query db-spec ["select * from todos where id = ?" id] {:row-fn row->todo})))

(defn create-todo!
  [todo]
  (let [incomplete-todo (merge todo {:completed false})]
    (let [id (sql-result->id
               (sql/insert! db-spec :todos (todo->row incomplete-todo)))]
      (merge incomplete-todo {:id id}))))


(defn delete-all!
  []
  (sql/delete! db-spec :todos []))