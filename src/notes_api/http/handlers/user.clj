(ns notes-api.http.handlers.user
  (:require
    [cheshire.core :as json]
    [io.pedestal.log :as log]
    [malli.core :as m]
    [malli.error :as me]
    [notes-api.user.interface :as user]
    [ring.util.response :as ring-resp]))


(def valid-user? (m/validator user/schema))


(defn humanize-error
  [user]
  (->
    (m/explain user/schema user)
    (me/humanize)))


(comment
  (def xuser {:name "Clauber"})
  (valid-user? xuser)
  (humanize-error xuser))


(defn create-user
  "[POST] create-user"
  [{:keys [json-params system-context]}]
  (if (valid-user? (:user json-params))
    (-> (user/create-user (assoc (:user json-params) :db system-context))
        (json/generate-string)
        (ring-resp/response))
    (do
      (log/error :create-user "This isn't a valid user")
      (ring-resp/bad-request
        (json/generate-string {:errors (humanize-error (:user json-params))})))))
