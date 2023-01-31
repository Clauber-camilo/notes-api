(ns notes-api.migrations
  (:require
    [migratus.core :as migratus]
    [notes-api.database :refer [db]]))


(def config
  {:store  :database
   :migration-dir "migrations/"
   :migration-table-name "migrations_meta"
   :db {:datasource (db)}})


(comment 
  ; create a new migration
  (migratus/create config "notes")
  ; rollback the migrations
  (migratus/rollback config)
  ;check the pending migrations
  (migratus.core/pending-list config))


;; run the migrations
(migratus/migrate config)
