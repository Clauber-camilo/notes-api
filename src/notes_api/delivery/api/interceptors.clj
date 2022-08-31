(ns notes-api.delivery.api.interceptors
  (:require
   [io.pedestal.http :as http]
   [io.pedestal.http.body-params :as body-params]))

;; The interceptors defined after the verb map (e.g., {:get home-page}
;; apply to / and its children (/about).
(def common-interceptors [(body-params/body-params) http/html-body])

