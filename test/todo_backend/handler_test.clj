(ns todo-backend.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [todo-backend.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello World"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))))

  (testing "documents route"
    (let [response (app (mock/request :get "/documents"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))))

  (testing "document route"
    (let [response (app (mock/request :get "/documents/2"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))))

  (testing "document deletion"
    (let [response (app (mock/request :delete "/documents/2"))]
      (is (= (:status response) 204))))

  (testing "document creation"
    (let [response (app (mock/request :post "/documents"))]
      (is (= (:status response) 201))))

  (testing "document updating"
    (let [response (app (mock/request :put "/documents/2"))]
      (is (= (:status response) 204)))))
