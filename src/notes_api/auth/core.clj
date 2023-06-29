(ns notes-api.auth.core
  (:require [io.pedestal.log :as log]
            [notes-api.user.core :refer [get-user-by-email]]
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
  (let [user (get-user-by-email email db)]
    (log/info :query user)
    (if (= (count user) 0)
      (do (log/error :zica "nao achou")
          {:error "User not found"})
      (do (log/info :zica user)
          {:success user}))))
