(ns tooling.client-request
  (:require
    [clj-http.client :as client]))


(defn create-user
  []
  (client/post "http://localhost:8080/user"
               {:content-type :json
                :form-params
                {:user {:name "Jonh Doe"
                        :email "test@test.com"
                        :password "123456678910@"}}}))
