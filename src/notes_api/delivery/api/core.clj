(ns notes-api.delivery.api.core
  (:require
   [io.pedestal.http.route :as route]
   [notes-api.delivery.api.interceptors.core :refer [common-interceptors]]
   [notes-api.delivery.api.testHandler :refer [test-handler]]
   [ring.util.response :as ring-resp]))

(defn about-page
  [_]
  (ring-resp/response (format "Clojure %s - served from %s"
                              (clojure-version)
                              (route/url-for ::about-page))))

(defn home-page
  [_]
  (ring-resp/response "Hello World!"))

;; Tabular routes
(def routes #{["/" :get (conj common-interceptors `home-page)]
              ["/about" :get (conj common-interceptors `about-page)]
              ["/test" :post (conj common-interceptors `test-handler) :route-name :test-page]})

; ; Map-based routes
; (def routes `{"/" {:interceptors common-interceptors
;                    :get home-page
;                    "/about" {:get about-page}}})

;; Terse/Vector-based routes
;(def routes
;  `[[["/" {:get home-page}
;      ^:interceptors [(body-params/body-params) http/html-body]
;      ["/about" {:get about-page}]]]])


