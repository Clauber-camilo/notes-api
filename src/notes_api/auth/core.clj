(ns notes-api.auth.core
  (:require [crypto.password.bcrypt :as crypto]
            [io.pedestal.log :as log]
            [malli.core :as m]
            [malli.error :as me]
            [notes-api.user.core :refer [get-user-by-email]]
            [notes-api.utils :refer [email-pattern password-pattern]]))

(def schema
  [:map {:closed true}
   [:password
    [:re {:error/message "Must have more than 10 characters and at least one of '@','!'"}
     password-pattern]]
   [:email email-pattern]])

(def valid-auth-body? (m/validator schema))
(defn humanize-error
  [auth-body]
  (->
   (m/explain schema auth-body)
   (me/humanize)))

(defn authenticate-user
  "Authenticate user"
  [email password db]
  (let [user (first (get-user-by-email email db))]
    (cond
      (not (valid-auth-body? {:email email :password password}))
      {:error (humanize-error {:email email :password password}) :status 500}

      (= (count user) 0)
      {:error "User not found" :status 404}

      (not (crypto/check password (:users/password user)))
      {:error "Something went wrong" :status 500}

      :else
      {:success {:name (:users/name user)
                 :email (:users/email user)
                 :level (:users/level user)
                 :created_at (:users/created_at user)
                 :updated_at (:users/updated_at user)}
       :status 200})))

(comment
  (require '[notes-api.database :refer [db]])
  (authenticate-user "test@test.com" "123456678910@"
                     #_{:clj-kondo/ignore [:unresolved-symbol]}
                     (db)))
