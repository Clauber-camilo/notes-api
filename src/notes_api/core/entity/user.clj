(ns notes-api.core.entity.user
  (:require
   [notes_api.core.entity.main :as entity]))

(defn- date
  "Creates a date in a target agnostic way"
  []
  java.util.Date.)

(defn create-user-obj [{:keys [name email password]}]
  {:id entity/make-uuid
   :name name
   :email email
   :password password
   :created-at (date)
   :updated-at (date)
   :deleted-at nil})

(defn create-user
  "Create a new User"
  [user]
  (->
   (create-user-obj user)
   (prn)))
