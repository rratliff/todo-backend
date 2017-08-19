(ns todo-backend.handler
  (:use cheshire.core)
  (:require [ring.middleware.defaults :refer :all]
            [ring.middleware.json :as middleware]
            [todo-backend.routes :as routes]))

(def app
  (-> routes/app-routes
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)
      (wrap-defaults api-defaults)))
