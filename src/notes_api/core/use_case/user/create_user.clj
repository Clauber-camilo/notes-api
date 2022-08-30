(ns notes-api.core.use-case.user.create-user
  (:require
   [crypto.password.bcrypt :as password]
   [notes-api.core.entity.user :as user]))

(defn encrypt [word]
  (password/encrypt word))

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

(defn create-user [user]
  (->
   (update user :password encrypt)
   (user/create-user)))

(comment
  (def xuser {:name "Clauber"
              :email "clauber@test.com"
              :password "12345@3dk"})
  (create-user xuser))
