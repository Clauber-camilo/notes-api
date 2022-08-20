(ns notes-api.core.entity.user (:require [notes_api.core.entity.core :as entity]))

(defn- date
  "Creates a date in a target agnostic way"
  []
  java.util.Date.)

(defn create-user
  "Create a new User"
  ([id name surname password] {::entity/id id
                               ::name name
                               ::surname surname
                               ::password password
                               ::created-at (date)
                               ::updated-at (date)})
  ([name surname password] (create-user (entity/make-uuid) name surname password)))
