(ns notes-api.schemas.user)

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
