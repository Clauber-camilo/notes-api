(ns notes-api.migrations
  (:require [migratus.core :as migratus]
            [notes-api.database :refer [db]]))


(def config
  {:store :database
   :migration-dir "migrations/"
   :migration-table-name "migrations_meta"
   :db {:datasource (db)}})


(comment
  ; create a new migration
  (migratus/create config "create-token-management")
  ; rollback the migrations
  (migratus/rollback config)
  ; run the migrations
  (migratus/migrate config)
  ;check the pending migrations
  (migratus.core/pending-list config))

