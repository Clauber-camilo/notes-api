(ns notes-api.database
  (:require
   [next.jdbc :as jdbc]))

(def db-config
  {:dbtype "postgresql",
   :dbname "notes-db",
   :user "notes-db" ,
   :password "notes-db" ,
   :host "localhost" ,
   :port 5432})

(defn db [] (jdbc/get-datasource db-config))

(comment
  (with-open [conn (jdbc/get-connection db-config)]
    (jdbc/execute! conn ["
        create table address (
          id serial primary key,
          name varchar(32),
          email varchar(255)
        )"])))

