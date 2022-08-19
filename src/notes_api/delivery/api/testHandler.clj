(ns notes-api.delivery.api.testHandler 
  (:require
   [io.pedestal.log :as log]
   [ring.util.response :as ring-resp]))

(defn test-handler
  [{:keys [json-params]}]
  (log/info :params json-params)
  (ring-resp/response (str "Hello " (:name json-params) " From Handlers v2")))
