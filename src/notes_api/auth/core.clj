(ns notes-api.auth.core
  (:require [honey.sql :as sql]
            [io.pedestal.log :as log]
            [next.jdbc :as jdbc]
            [notes-api.utils :refer [email-pattern password-pattern]]))

(def schema
  [:map {:closed true}
   [:password
    [:re {:error/message "Must have more than 10 characters and at least one of '@','!'"}
     password-pattern]]
   [:email email-pattern]])

(defn authenticate-user
  "Authenticate user"
  [email password db]
  (let [user (jdbc/execute! db (sql/format {:select :*
                                            :from :users
                                            :where [:= :email email]}))]
    (log/info :query user)
    (if (= (count user) 0)
      (do (log/error :zica "nao achou")
          {:error "User not found"})
      (log/info :zica "achou"))))
