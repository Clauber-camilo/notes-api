(ns notes-api.delivery.api.handlers.user
  (:require
    [io.pedestal.log :as log]))

(defn validate-user [user]
  (and
    (seq user)
    (seq (:name user))))


(defn create-user-handler [{:keys [json-params]}])

