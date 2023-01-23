(ns notes-api.user.interface
  "This namespace serves as a form of contract to outside code,
   so no place should require user.core directly, thus,
   avoid changing function signatures here"
  (:require
    [notes-api.user.core :as user]))


(def schema user/schema)


;; Using maps as parameters make it easier to evolve
;; likely to change interfaces
(defn create-user
  [{:keys [id name surname password]}]
  (user/create-user id name surname password))
