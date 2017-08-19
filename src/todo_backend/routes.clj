(ns todo-backend.routes
  (:use ring.util.response)
  (:require [compojure.route :as route]
            [compojure.core :refer :all]
            [todo-backend.repository :as store]))

(defn- document-representation [document]
  document)

(defn- res->ok [body]
  {:status 200 :body body})

(defn- res->created [todo]
  {:status 201
   :body todo})

(defn get-all-documents [] (->
                             (store/get-all)
                             (#(map document-representation %))
                             (res->ok)))

(defn create-new-document [body] (let [new-document (store/create-document! body)]
                                   (res->created new-document)))

(defn get-document [id] (response {:name "Test document" :body "Test body"}))

(defn update-document [id, body] {:status 204})

(defn delete-document [id] {:status 204})

(defroutes app-routes
           (context "/documents" [] (defroutes documents-routes
                                               (GET "/" [] (get-all-documents))
                                               (POST "/" {body :body} (create-new-document body))
                                               (context "/:id" [id] (defroutes document-routes
                                                                               (GET "/" [] (get-document id))
                                                                               (PUT "/" {body :body} (update-document id body))
                                                                               (DELETE "/" [] (delete-document id))))))
           (GET "/" [] "Hello World")
           (route/not-found "Not Found"))
