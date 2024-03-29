(ns notes-api.http.server
  (:gen-class)                                              ; for -main method in uberjar
  (:require
   [io.pedestal.http :as server]
   [io.pedestal.http.route :as route]
   [notes-api.http.service :as service]
   [notes-api.database :as database]
   [notes-api.http.routes :refer [routes]]))

;; This is an adapted service map, that can be started and stopped
;; From the REPL you can call server/start and server/stop on this service
(defonce runnable-service (server/create-server service/service))

(defn run-dev
  "The entry-point for 'lein run-dev'"
  [& _]
  (println "\nCreating your [DEV] server...")
  (let [db (database/db)]
    (-> service/service                                       ;; start with production configuration
        (merge {:env :dev
                ::atm-con "DB"
                ;; do not block thread that starts web server
                ::server/join? false
                ;; Routes can be a function that resolve routes,
                ;;  we can use this to set the routes to be reloadable
                ::server/routes #(route/expand-routes ((deref #'routes) db))
                ;; all origins are allowed in dev mode
                ::server/allowed-origins {:creds true :allowed-origins (constantly true)}
                ;; Content Security Policy (CSP) is mostly turned off in dev mode
                ::server/secure-headers {:content-security-policy-settings {:object-src "'none'"}}})
        ;; Wire up interceptor chains
        server/default-interceptors
        server/dev-interceptors
        server/create-server
        server/start)))

(defn -main
  "The entry-point for 'lein run'"
  [& _]
  (println "\nCreating your server...")
  (server/start runnable-service))


