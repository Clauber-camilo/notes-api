(ns notes-api.database
  (:require
    [environ.core :refer [env]]
    [next.jdbc :as jdbc]))


(def db-environment
  (read-string (env :database)))


(def db-config
  {:dbtype "postgresql",
   :dbname (:dbname db-environment),
   :user (:user db-environment),
   :password (:password db-environment),
   :host (:host db-environment) ,
   :port (:port db-environment)})


(defn db
  []
  (jdbc/get-datasource db-config))
