(defproject minimal_webserver "1.0.0"
  :description "Minimal Server"
  :url "https://otto.de"

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.jakemccrary/lein-test-refresh "0.24.1"]
                 [http-kit "2.4.0"]
                 [org.clojure/data.json "1.0.0"]
                 [compojure "1.6.2"]
                 [hiccup "1.0.5"]]

  :main webserver.core
  :repl-options {:init-ns webserver.core}
  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.24.1"]]}})
