(ns notes-api.user.core
  (:require [crypto.password.bcrypt :as crypto]
            [honey.sql :as sql]
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

(defn create-user
  "Create a new User"
  [id name email password db]
  (let [hashed-password (crypto/encrypt password)]
    (jdbc/execute! db (sql/format {:insert-into :users
                                   :columns [:id :name :email :password]
                                   :values [[(or id (UUID/randomUUID)) name email hashed-password]]}))))

(defn get-user-by-email
  "Get a User by email"
  [email db]
  (jdbc/execute! db (sql/format {:select :*
                                 :from :users
                                 :where [:= :email email]})))
