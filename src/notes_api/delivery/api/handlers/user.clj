(ns notes-api.delivery.api.handlers.user
  (:require
   [io.pedestal.log :as log]))

(defn validate-user [user] (and
                            (seq user)
                            (seq (:name user))))

(defn create-user-handler [{:keys [json-params]}] (let [created-user (let [is-valid (validate-user json-params)]
                                                                       (log/info :xablau is-valid))]))

