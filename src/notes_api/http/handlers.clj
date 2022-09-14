(ns notes-api.http.handlers
  (:require
   [io.pedestal.http.route :as route]
   [next.jdbc :as jdbc]
   [ring.util.response :as ring-resp]))

; (defn test-handler
;   [{:keys [json-params]}]
;   (log/info :params json-params)
;   (ring-resp/response
;    (str "Hello "
;         (:name json-params)
;         " From Handlers v2")))

(defn test-handler [{:keys [db]}]
  (jdbc/execute!
   db ["
      create table address (
        id int auto_increment primary key,
        name varchar(32),
        email varchar(255)
      )"]))

(defn about-page
  [_]
  (ring-resp/response (format "Clojure %s - served from %s"
                              (clojure-version)
                              (route/url-for ::about-page))))

(defn home-page
  [_]
  (ring-resp/response "Hello World!"))
