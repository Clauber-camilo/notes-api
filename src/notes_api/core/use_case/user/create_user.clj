(ns notes-api.core.use-case.user.create-user
  (:require [crypto.password.bcrypt :as password]))

(comment
  (def xuser {:name "Clauber"
              :email "clauber@test.com"
              :password "12345@3dk"}))

(defn encrypt [word]
  (password/encrypt word))

(defn transform-user [user]
  {:name (:name user)
   :email (:email user)
   :password (encrypt (:password user))})

(defn create-user [user]
  (->
   (transform-user user)
   (prn)))

(comment
  (create-user xuser))
