(ns todo-backend.handler
  (:use cheshire.core)
  (:require [ring.middleware.defaults :refer :all]
            [ring.middleware.json :as middleware]
            [todo-backend.routes :as routes]
            [todo-backend.middleware :as todo-middleware]))

(def app
  (-> routes/app-routes
      (todo-middleware/wrap-cors)
      (todo-middleware/wrap-response-expand-url-body)
      (todo-middleware/wrap-response-expand-location)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)
      (wrap-defaults api-defaults)))
