(ns notes-api.http.interceptors
  (:require
   [io.pedestal.http :as http]
   [io.pedestal.http.body-params :as body-params]
   [next.jdbc :as jdbc]))

;; The interceptors defined after the verb map (e.g., {:get home-page}
;; apply to / and its children (/about).
(def common-interceptors [(body-params/body-params) http/html-body])

(def db-config
  {:dbtype "postgresql",
   :dbname "notes-db",
   :user "notes-db" ,
   :password "notes-db" ,
   :host "localhost" ,
   :port 5432})

(def db (jdbc/get-datasource db-config))

(comment
  (with-open [conn (jdbc/get-connection db-config)]
    (jdbc/execute! conn ["
        create table address (
          id serial primary key,
          name varchar(32),
          email varchar(255)
        )"])))

(def db-connection
  {:name ::db-connection
   :enter (fn [context]
            (update context :request assoc :db db))})
