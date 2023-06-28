(ns notes-api.http.handlers.auth
  (:require [cheshire.core :as json]
            [io.pedestal.log :as log]
            [malli.core :as m]
            [malli.error :as me]
            [notes-api.auth.interface :as auth]
            [ring.util.response :as ring-resp]))

(def valid-auth-body? (m/validator auth/schema))

(defn humanize-error
  [auth-body]
  (->
   (m/explain auth/schema auth-body)
   (me/humanize)))

(comment
  (do
    (def xuser {:email "teste@teste.com" :password "1234567890!"})
    (valid-auth-body? xuser)
    (humanize-error xuser)))

(defn authenticate-user
  "[POST] authenticate-user"
  [{:keys [json-params system-context]}]
  (if (valid-auth-body? json-params)
    (let [response (auth/authenticate-user (assoc json-params :db system-context))]
      (if (contains? response :error)
        (do
          (log/error :authenticate-user "User not found")
          {:status 404 :body (json/generate-string response)})
        (ring-resp/response (json/generate-string response))))
    (do
      (log/error :authenticate-user "This isn't a valid body")
      (ring-resp/bad-request (json/generate-string {:errors (humanize-error json-params)})))))
