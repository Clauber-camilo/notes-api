(ns notes-api.http.handlers
  (:require
    [io.pedestal.http.route :as route]
    [io.pedestal.log :as log]
    [ring.util.response :as ring-resp]))


(defn test-handler
  [{:keys [json-params system-context]}]
  (log/info :db-test (:db system-context))
  (ring-resp/response
    (str "Hello "
         (:name json-params)
         " From Handlers v2")))


(defn about-page
  [_]
  (ring-resp/response (format "Clojure %s - served from %s"
                              (clojure-version)
                              (route/url-for ::about-page))))


(defn home-page
  [_]
  (ring-resp/response "Hello World!"))
