(ns notes-api.http.interceptors)


;; The interceptors defined after the verb map (e.g., {:get home-page}
;; apply to / and its children (/about).
;; (defn common-interceptors  [] [(body-params/body-params) http/html-body])

(defn assoc-db
  [db]
  {:name ::assoc-db
   :enter (fn [context]
            (assoc-in context [:request :system-context] db))})
