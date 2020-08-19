(ns webserver.core
  (:require [org.httpkit.server :as httpkit]
            [clojure.data.json :as json]
            [compojure.core :as cpj]
            [ring.util.response :as response]
            [webserver.example-data :as example-data]))

(defn respond-json [data]
  (response/content-type
    (response/response (json/write-str data))
    "application/json; charset=utf-8"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(cpj/defroutes define-routes
               (cpj/GET "/node-data/:node-id" [node-id] (respond-json {}))
               (cpj/GET "/navigation-nodes" [] (respond-json example-data/nested-nodes)))

(defn start [{:keys [port]
              :or   {port 8080}}]
  (httpkit/run-server define-routes {:port port}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn -main [& args]
  (start {}))