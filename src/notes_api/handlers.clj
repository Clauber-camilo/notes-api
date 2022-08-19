(ns notes-api.handlers
  (:require [ring.util.response :as ring-resp]
            [io.pedestal.log :as log]))

(defn test-handler
  [{:keys [json-params]}]
  (log/info :params json-params)
  (ring-resp/response (str "Hello " (:name json-params) " From Handlers")))

