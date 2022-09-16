(ns notes-api.http.routes
  (:require
   [io.pedestal.http.body-params :as body-params]
   [notes-api.http.handlers :as h]
   [notes-api.http.interceptors :as i]))

(defn routes [db]
  [[["/"
     ; ^:interceptorfn `i/common-interceptors
     ^:interceptors [(body-params/body-params) (i/assoc-db db)]
     {:get `h/home-page}
     ["/about"
      {:get `h/about-page}]
     ["/test"
      {:post `h/test-handler}]]]])

