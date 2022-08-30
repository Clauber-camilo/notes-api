(ns notes-api.core.entity.user
  (:require
   [notes-api.core.entity.entity-base :as entity]
   [clojure.data.json :as json]))

(defn- date
  "Creates a date in a target agnostic way"
  []
  (java.util.Date.))

(defn create-user-obj [{:keys [name email password]}]
  {:id (entity/make-uuid-str)
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
   (dissoc :password)
   (json/write-str)))

(comment
  (def xuser {:name "Clauber"
              :password "34341234234$34324"
              :email "clauber@test.com"})
  (create-user xuser))
