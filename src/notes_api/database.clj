(ns notes-api.database
  (:require [aero.core :refer [read-config]]
            [clojure.java.io :as io]
            [next.jdbc :as jdbc]))


(def db-environment
  (:database (read-config (io/resource "config.edn"))))


(def db-config
  {:dbtype "postgresql",
   :dbname (:dbname db-environment),
   :user (:user db-environment),
   :password (:password db-environment),
   :host (:host db-environment),
   :port (:port db-environment)})


(defn db
  []
  (jdbc/get-datasource db-config))
