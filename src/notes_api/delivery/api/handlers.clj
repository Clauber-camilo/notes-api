(ns notes-api.delivery.api.handlers
  (:require
   [io.pedestal.log :as log]
   [clojure.data.json :as json]
   [malli.core :as m]
   [malli.error :as me]
   [notes-api.components.user.core :as user]
   [ring.util.response :as ring-resp]))

(def valid-user? (m/validator user/schema))

(defn humanize-error [user]
  (->
   (m/explain user/schema user)
   (me/humanize)))

(defn write-error-json [e]
  (json/write-str {:errors (humanize-error e)}))

(defn create-user-handler
  "[POST] create-user"
  [{{:keys [user]} :json-params}]
  (if (valid-user? user)
    (-> (user/create user)
        (ring-resp/response))
    (do
      (log/error :create-user "This isn't a valid user")
      (ring-resp/bad-request (write-error-json user)))))

