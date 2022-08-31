(ns notes-api.components.user.entity
  (:require
   [clojure.data.json :as json]))

(defn- make-uuid-str
  "Creates a new UUID and return a string" []
  (str (java.util.UUID/randomUUID)))

(defn- date
  "Creates a date in a target agnostic way"
  []
  (java.util.Date.))

(defn generate-user-map [{:keys [name email password]}]
  {:id (make-uuid-str)
   :name name
   :email email
   :password password
   :created-at (date)
   :updated-at (date)
   :deleted-at nil})

(defn save-user
  "Create a new User"
  [user]
  (->
   (generate-user-map user)
   (dissoc :password)
   (json/write-str)))

(comment
  (def xuser {:name "Clauber"
              :password "34341234234$34324"
              :email "clauber@test.com"})
  (save-user xuser))

