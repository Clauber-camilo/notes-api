(ns notes-api.user.core
  (:require [malli.core :as m])
  (:import [java.util Date UUID]))

(defn- date
  "Creates a date in a target agnostic way"
  []
  (Date.))
(comment
  ;; turn instrumentation on
  (require `[malli.instrument :as mi])
  (mi/instrument!))

(defn create-user
  "Create a new User"
  [id name surname password]
  {:id (or id (UUID/randomUUID))
   :name name
   :surname surname
   :password password
   :created-at (date)
   :updated-at (date)})
(m/=> create-user
      [:=>
       [:cat uuid? string? string? string?]
       map?])
