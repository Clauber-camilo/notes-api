(ns notes-api.http.routes
  (:require
    [io.pedestal.http.body-params :as body-params]
    [notes-api.http.handlers :as h]
    [notes-api.http.handlers.user :as u]
    [notes-api.http.interceptors :as i]))


(defn routes
  [db]
  [[["/"
     ;; ^:interceptorfn `i/common-interceptors
     ^:interceptors [(body-params/body-params) (i/assoc-db db)]
     {:get `h/home-page}
     ["/about"
      {:get `h/about-page}]
     ["/user"
      {:post `u/create-user}]
     ["/test"
      {:post `h/test-handler}]]]])
