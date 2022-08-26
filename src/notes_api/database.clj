(ns notes-api.database
  (:require [xtdb.api :as xt]))

(defonce node (xt/start-node {}))

(def db (partial xt/db node))

(defn save
  "save helper"
  [node & entities]
  (xt/submit-tx
    node
    (mapv (fn [e]
            [::xt/put e]) entities)))

(defn query
  ([query-map] (query (db) query-map))
  ([db query-map]
   (xt/q db query-map)))

(comment
  (db)
  (save node
        {:xt/id "okok"
         :user/name "Kaue"}
        {:xt/id "fuu"
         :user/name "jeska"})

  (save node {:xt/id "fuu"
              :user/name "jeska"
              :user/email "jeska@mail.com"})

  (save node {:xt/id "fuu"
              :user/name "jeska"
              :user/email "jeska@mail.com"
              :user/password "123456789"})
  ;;query stuff
  (query '{:find [e (pull e [*])]
          :where [[e :user/name "jeska"]]})

  ;;list transactions where entity changes + the document itself
  (xt/entity-history
    (db)
    "fuu" :desc {:with-docs? true})

  )