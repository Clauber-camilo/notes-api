(ns notes-api.delivery.api.handlers.user
  (:require
   [io.pedestal.log :as log]
   [clojure.data.json :as json]
   [malli.core :as m]
   [malli.error :as me]
   [notes-api.schemas.user :refer [user-schema]]
   [ring.util.response :as ring-resp]))

; (comment
;   ;;validate data using a schema
;   (m/validate user-schema {:name "Kaue Matos"
;                            :password "1234567!89"
;                            :email "somemail@fubanga.com"})
;
;   ;;define a validator function for a fixed schema
;   ;;more time-efficient
;   (def valid-user? (m/validator user-schema))
;   (valid-user? {:name "Kaue Matos"
;                 :password "12345678@9"
;                 :email "somemail@fubanga.com"})
;
;   ;;Explain what's wrong
;   (def error-data
;     (m/explain user-schema
;                {:name "Kaue Matos"
;                 :password "burundanga"
;                 :email "paranaue.com"
;                 :jabiraca 72
;                 :additional-info {:location {:lat 22
;                                              :milambe true
;                                              :long -4.255}}}))
;
;   ;;Explain error in human readable format
;   (me/humanize error-data))

(def valid-user? (m/validator user-schema))

(defn humanize-error [user]
  (->
   (m/explain user-schema user)
   (me/humanize)))

(defn write-error-json [e]
  (json/write-str {:errors (humanize-error e)}))

(defn create-user-handler
  "[POST] create-user"
  [{{:keys [user]} :json-params}]
  (if (valid-user? user)
    (log/info :create-user "foi")
    (do
      (log/error :create-user "This isn't a valid user")
      (ring-resp/bad-request (write-error-json user)))))

