(ns notes-api.auth.interface
  (:require [notes-api.auth.core :as auth]))

(def schema auth/schema)

(defn authenticate-user
  [{:keys [email password db]}]
  (auth/authenticate-user email password db))
