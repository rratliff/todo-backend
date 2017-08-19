(ns todo-backend.routes
  (:use ring.util.response)
  (:require [compojure.route :as route]
            [compojure.core :refer :all]
            [todo-backend.repository :as store]))

(defn- todo-representation [document]
  document)

(defn- res->ok [body]
  {:status 200 :body body})

(defn- res->created [todo]
  {:status 201
   :body todo})

(defn- res->no-content []
  {:status 204})

(defn get-all-todos [] (->
                             (store/get-all)
                             (#(map todo-representation %))
                             (res->ok)))

(defn delete-all-todos []
  (store/delete-all!)
  (res->no-content))

(defn create-new-todo [body] (let [new-todo (store/create-todo! body)]
                                   (res->created new-todo)))

(defn get-document [id] (response {:name "Test document" :body "Test body"}))

(defn update-document [id, body] {:status 204})

(defn delete-document [id] {:status 204})

(defroutes app-routes
           (context "/todos" [] (defroutes documents-routes
                                               (GET "/" [] (get-all-todos))
                                               (DELETE "/" [] (delete-all-todos))
                                               (POST "/" {body :body} (create-new-todo body))
                                               (context "/:id" [id] (defroutes document-routes
                                                                               (GET "/" [] (get-document id))
                                                                               (PUT "/" {body :body} (update-document id body))
                                                                               (DELETE "/" [] (delete-document id))))))
           (GET "/" [] "Hello World")
           (route/not-found "Not Found"))
