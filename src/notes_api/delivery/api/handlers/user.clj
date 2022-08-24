(ns notes-api.delivery.api.handlers.user
  (:require
   [io.pedestal.log :as log]
   [clojure.string :as s]))

(def qualquer-coisa {:name "jose"
                     :password "12345"
                     :email "jose@jose.com"})

(defn not-blank [s] (not (s/blank? s)))

(defn valid-user? [{:keys [name
                           password
                           email]}]
  (boolean
   (and
    (not-blank name)
    (not-blank password)
    (not-blank email))))

(defn create-user-handler [{{:keys [user]} :json-params :as request}]
  (def x request)
  (if (valid-user? user)
    (log/info :create-user "foi")
    (log/error :create-user "to bad")))

(prn x)
