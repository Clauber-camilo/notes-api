(ns notes-api.user.core
  (:require
    [malli.core :as m])
  (:import
    (java.util
      Date
      UUID)))


(def name-pattern #"^.{8,}$")
(def password-pattern #"(?=^.{10,}$)(?=.*(\@|\!))")
(def email-pattern #"^[a-z0-9]+@.[a-z]")


(def schema
  [:map {:closed true}
   [:name name-pattern]
   [:password
    [:re {:error/message "Must have more than 10 characters and at least one of '@','!'"}
     password-pattern]]
   [:email email-pattern]])


(defn- date
  "Creates a date in a target agnostic way"
  []
  (Date.))


(comment
  ;; turn instrumentation on
  (require `[malli.instrument :as mi])
  #_{:clj-kondo/ignore [:unresolved-namespace]}
  (mi/instrument!))


(defn create-user
  "Create a new User"
  [id name surname password]
  [{:id (or id (UUID/randomUUID))
    :name name
    :surname surname
    :password password
    :created-at (date)
    :updated-at (date)}])


(m/=> create-user
      [:=>
       [:cat uuid? string? string? string?]
       map?])
