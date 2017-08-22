(ns todo-backend.routes
  (:use ring.util.response)
  (:require [compojure.route :as route]
            [compojure.core :refer :all]
            [todo-backend.repository :as store]))

(defn- todo-representation [todo]
  (merge todo {:url (str "/todos/" (:id todo))}))

(defn- res->ok [body]
  {:status 200 :body body})

(defn- res->created [todo]
  {:status  201
   :body    todo})

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
                                   (res->created (todo-representation new-todo))))

(defn get-todo [id] (-> (store/get-by-id id)
                        (todo-representation)
                        (res->ok)))

(defn update-todo [id, body] (let [updated-todo (store/update-todo! id body)]
                               (res->ok (todo-representation updated-todo))))

(defn delete-todo [id]
  (store/delete-by-id id)
  (res->no-content))

(defroutes app-routes
           (context "/todos" [] (defroutes todo-routes
                                               (GET "/" [] (get-all-todos))
                                               (DELETE "/" [] (delete-all-todos))
                                               (POST "/" {body :body} (create-new-todo body))
                                               (context "/:id" [id] (defroutes todo-routes
                                                                               (GET "/" [] (get-todo id))
                                                                               (PATCH "/" {body :body} (update-todo id body))
                                                                               (DELETE "/" [] (delete-todo id))))))
           (GET "/" [] "Hello World")
           (route/not-found "Not Found"))
