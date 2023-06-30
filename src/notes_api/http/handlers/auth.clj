(ns notes-api.http.handlers.auth
  (:require [cheshire.core :as json]
            [notes-api.auth.interface :as auth]))

(defn authenticate-user
  "[POST] authenticate-user"
  [{:keys [json-params system-context]}]
  (let [auth-result (auth/authenticate-user (assoc json-params :db system-context))
        response (cond
                   (:error auth-result)
                   (assoc auth-result :body (:error auth-result))

                   (:success auth-result)
                   (assoc auth-result :body (:success auth-result))

                   :else
                   (assoc auth-result :body (:error auth-result)))]
    (update response :body json/generate-string (:body response))))
