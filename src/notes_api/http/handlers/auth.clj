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

(defn authenticate-user
  "[POST] authenticate-user"
  [{:keys [json-params system-context]}]
  (if (valid-auth-body? json-params)
    (-> (auth/authenticate-user (assoc json-params :db system-context))
        (#(cond
            (contains? % :error) {:status 404 :body (json/generate-string %)}
            (contains? % :success) {:status 200 :body (json/generate-string (:success %))})))
    (do
      (log/error :authenticate-user "This isn't a valid body")
      (ring-resp/bad-request (json/generate-string {:error (humanize-error json-params)})))))

(comment
  (do
    (def xuser {:email "teste@teste.com" :password "1234567890!"})
    (valid-auth-body? xuser)
    (humanize-error xuser)))
