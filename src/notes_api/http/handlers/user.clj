(ns notes-api.http.handlers.user
  (:require [notes-api.user.interface :as user]
            [io.pedestal.log :as log]
            [ring.util.response :as ring-resp]
            [cheshire.core :as json]))

(defn create-user [{:keys [json-params]}]
  (log/info :params json-params)
  (let [params (:user json-params)
        new-user (user/create-user params)]
    (ring-resp/response (json/generate-string new-user))))
