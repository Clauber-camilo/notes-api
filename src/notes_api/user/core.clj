(ns notes-api.user.core
  (:require [crypto.password.bcrypt :as crypto]
            [honey.sql :as sql]
            [malli.core :as m]
            [next.jdbc :as jdbc]
            [notes-api.utils :refer [email-pattern name-pattern password-pattern]])
  (:import (java.util
            UUID)))

(def schema
  [:map {:closed true}
   [:name name-pattern]
   [:password
    [:re {:error/message "Must have more than 10 characters and at least one of '@','!'"}
     password-pattern]]
   [:email email-pattern]])


(comment
  ;; turn instrumentation on
  (require `[malli.instrument :as mi])
  #_{:clj-kondo/ignore [:unresolved-namespace]}
  (mi/instrument!))


(defn create-user
  "Create a new User"
  [id name email password db]
  (let [hashed-password (crypto/encrypt password)]
    (jdbc/execute! db (sql/format {:insert-into :users
                                   :columns [:id :name :email :password]
                                   :values [[(or id (UUID/randomUUID)) name email hashed-password]]}))))

(m/=> create-user
      [:=>
       [:cat uuid? string? string? string?]
       map?])
