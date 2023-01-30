(ns notes-api.user.core
  (:require
    [honeysql.core :as sql]
    [honeysql.helpers :refer :all :as helpers]
    [malli.core :as m]
    [next.jdbc :as jdbc])
  (:import
    (java.util
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
  (sql/raw ["now()"]))


(comment
  ;; turn instrumentation on
  (require `[malli.instrument :as mi])
  #_{:clj-kondo/ignore [:unresolved-namespace]}
  (mi/instrument!))


(defn create-user
  "Create a new User"
  [id name email password db]
  (jdbc/execute! db (sql/format {:insert-into :users
                                 :columns [:id :name :email :password :created-at :updated-at]
                                 :values [[(or id (UUID/randomUUID)) name email password (date) (date)]]})))


;; [{:id (or id (UUID/randomUUID))
;;   :name name
;;   :email email
;;   :password password
;;   :created-at (date)
;;   :updated-at (date)}])


(m/=> create-user
      [:=>
       [:cat uuid? string? string? string?]
       map?])
