(ns notes-api.core.entity.entity-base)

(defn make-uuid
  "Creates a new UUID" []
  (str (java.util.UUID/randomUUID)))

(defn make-uuid-str
  "Creates a new UUID and return a string" []
  (str (make-uuid)))

