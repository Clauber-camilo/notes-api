(ns notes-api.auth.core
  (:require [aero.core :refer [read-config]]
            [buddy.sign.jwt :as jwt]
            [clj-time.core :as time]
            [clojure.java.io :as io]
            [crypto.password.bcrypt :as crypto]
            [honey.sql :as sql]
            [malli.core :as m]
            [malli.error :as me]
            [next.jdbc :as jdbc]
            [notes-api.user.core :refer [get-user-by-email]]
            [notes-api.utils :refer [email-pattern password-pattern]])
  (:import [java.util UUID]))

(def secret
  (:secret (read-config (io/resource "config.edn"))))

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

(defn generate-token
  "Generate a new jwt token"
  ([data] (generate-token data (time/plus (time/now) (time/minutes 30))))
  ([data expires]
   (println (format "data :%s" data))
   (jwt/sign (assoc data :exp expires) (str secret))))

(defn save-in-token-management
  "Save a new register in token managment and return a valid token and refresh"
  [user db]
  (let [token (generate-token user)
        refresh (generate-token user (time/plus (time/now) (time/days 30)))]
    (jdbc/execute! db (sql/format {:insert-into :token_management
                                   :columns [:id :token :user_id :refresh_token]
                                   :values [[(UUID/randomUUID) token (:id user) refresh]]}))
    {:token token :refresh_token refresh}))

(defn authenticate-user
  "Authenticate user"
  [email password db]
  (let [user (first (get-user-by-email email db))]
    (cond
      (not (valid-auth-body? {:email email :password password}))
      {:error (humanize-error {:email email :password password}) :status 500}

      (= (count user) 0)
      {:error "User not found" :status 404}

      (not (crypto/check password (:password user)))
      {:error "Something went wrong" :status 500}

      :else
      (let [{:keys [token refresh_token]} (save-in-token-management user db)]
        {:success {:name (:name user)
                   :email (:email user)
                   :level (:level user)
                   :token token
                   :refresh_token refresh_token
                   :created_at (:created_at user)
                   :updated_at (:updated_at user)}
         :status 200}))))

(comment
  (require '[notes-api.database :refer [db]])
  (authenticate-user "test@test.com" "123456678910@"
                     #_{:clj-kondo/ignore [:unresolved-symbol]}
                     (db))
  (save-in-token-management {:id (UUID/fromString "fc5c9ca6-34c7-4a18-8fe1-1617a708f040") :name "Jonh Doe"} (db)))
