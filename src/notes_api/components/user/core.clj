(ns notes-api.components.user.core
  (:require
   [crypto.password.bcrypt :as password]
   [notes-api.components.user.entity :as entity]))

; (defn transform-user [user]
;   {:name (:name user)
;    :email (:email user)
;    :password (encrypt (:password user))})

; (defn create-user [user]
;   (let [password (encrypt (:password user))]
;     (assoc user :password password)))

; (defn create-user [user]
;   (->
;    (transform-user user)
;    (prn)))

(defn create [user]
  (->
   (update user :password password/encrypt)
   (entity/save-user)))

(comment
  (def xuser {:name "Clauber"
              :email "clauber@test.com"
              :password "12345@3dk"})
  (create xuser))

(def name-pattern #"^.{8,}$")
(def password-pattern #"(?=^.{10,}$)(?=.*(\@|\!))")
(def email-pattern #"^[a-z0-9]+@.[a-z]")

(def schema
  [:map {:closed true}
   [:name name-pattern]
   [:password
    [:re {:error/message "Must have more than 10 characters and at least one of '@','!'"}
     password-pattern]]
   [:email email-pattern]
   [:additional-info {:optional true}
    [:map
     [:location
      [:map {:closed true}
       [:lat double?]
       [:long double?]]]]]])
