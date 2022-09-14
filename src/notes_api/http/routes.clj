(ns notes-api.http.routes
  (:require [notes-api.http.interceptors :as i]
            [notes-api.http.handlers :as h]
            [notes-api.http.handlers.user :as user.handler]))

(def routes #{["/" :get (conj i/common-interceptors `h/home-page)]
              ["/about" :get (conj i/common-interceptors `h/about-page)]
              ["/test" :post (conj i/common-interceptors i/db-connection `h/test-handler) :route-name :test-page]
              ["/user" :post (conj i/common-interceptors `user.handler/create-user)]})
