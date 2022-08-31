(ns notes-api.delivery.api.core
  (:require
   [io.pedestal.http.route :as route]
   [notes-api.delivery.api.handlers :refer [create-user-handler]]
   [notes-api.delivery.api.interceptors :refer [common-interceptors]]
   [notes-api.delivery.api.testHandler :refer [test-handler]]
   [ring.util.response :as ring-resp]))

(defn about-page
  [_]
  (ring-resp/response
   (format "Clojure %s - served from %s"
           (clojure-version)
           (route/url-for ::about-page))))

(defn home-page
  [_]
  (ring-resp/response "Hello World!"))

; Routes definitions
(def routes
  #{["/" :get (conj common-interceptors `home-page)]
    ["/about" :get (conj common-interceptors `about-page)]
    ["/test" :post (conj common-interceptors `test-handler) :route-name :test-page]
    ["/user" :post (conj common-interceptors `create-user-handler) :route-name :create-user]})

