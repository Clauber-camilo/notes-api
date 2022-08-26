(ns notes-api.delivery.api.handlers.user
  (:require
    [clojure.string :as s]
    [clojure.string]
    [io.pedestal.log :as log]
    [malli.core :as m]
    [malli.error :as me]))

(defn not-blank [s] (not (s/blank? s)))

(def name-pattern #"^.{8,}$")
(def password-pattern #"(?=^.{10,}$)(?=.*(\@|\!))")
(def email-pattern #"^[a-z0-9]+@.[a-z]")


(def user-schema
  [:map {:closed true}
   [:name name-pattern]
   [:password
    [:re {:error/message "Must have more than 10 characters and at least one of '@','!'"}
     password-pattern]]
   [:email email-pattern]
   [:additional-info {:optional true}
    [:map
     [:location
      [:map {:closed true}
       [:lat double?]
       [:long double?]]]]]])

(comment
  ;;validate data using a schema
  (m/validate user-schema {:name "Kaue Matos"
                           :password "12345678@9"
                           :email "somemail@fubanga.com"})

  ;;define a validator function for a fixed schema
  ;;more time-efficient
  (def valid-user? (m/validator user-schema))
  (valid-user? {:name "Kaue Matos"
                :password "12345678@9"
                :email "somemail@fubanga.com"})

  ;;Explain what's wrong
  (def error-data
    (m/explain user-schema
               {:name "Kaue Matos"
                :password "burundanga"
                :email "paranaue.com"
                :jabiraca 72
                :additional-info {:location {:lat 22
                                             :milambe true
                                             :long -4.255}}}))

  ;;Explain error in human readable format
  (me/humanize error-data))


(defn valid-user? [{:keys [name
                           password
                           email]}]
  (boolean
    (and
      (not-blank name)
      (not-blank password)
      (not-blank email))))

(defn create-user-handler [{{:keys [user]} :json-params :as request}]
  (def x request)
  (if (valid-user? user)
    (log/info :create-user "foi")
    (log/error :create-user "to bad")))

(prn x)
