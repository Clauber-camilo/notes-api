(ns notes-api.http.client-requests
  (:require
   [io.pedestal.http :as server]
   [notes-api.http.server :refer [run-dev runnable-service]]))

(comment
  (def start-dev-serv (future (run-dev)))
  (server/stop @start-dev-serv))

(comment
  (require `clj-http.client)
  #_{:clj-kondo/ignore [:unresolved-namespace]}
  (clj-http.client/post
   "http://localhost:8080/test"
   {:form-params
    {:user {:name "Kaue"}}
    :content-type :json})
  #_{:clj-kondo/ignore [:unresolved-namespace]}
  (clj-http.client/get
   "http://localhost:8080/about"))
