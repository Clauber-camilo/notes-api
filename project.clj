(defproject notes-api "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [io.pedestal/pedestal.service "0.5.10"]
                 [metosin/malli "0.8.9"]
                 [clj-http "3.12.3"]
                 [com.github.seancorfield/next.jdbc "1.3.828"]
                 [org.postgresql/postgresql "42.3.7"]
                 ;; Remove this line and uncomment one of the next lines to
                 ;; use Immutant or Tomcat instead of Jetty:
                 [io.pedestal/pedestal.jetty "0.5.10"]
                 ;; [io.pedestal/pedestal.immutant "0.5.10"]
                 ;; [io.pedestal/pedestal.tomcat "0.5.10"]

                 [ch.qos.logback/logback-classic "1.2.3" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.26"]
                 [org.slf4j/jcl-over-slf4j "1.7.26"]
                 [org.slf4j/log4j-over-slf4j "1.7.26"]
                 [migratus "1.4.9"]
                 [com.github.seancorfield/honeysql "2.4.969"]
                 [aero "1.1.6"]
                 [clj-time "0.15.2"]
                 [crypto-password "0.3.0"]
                 [buddy/buddy-sign "3.5.351"]]
  :min-lein-version "2.0.0"
  :resource-paths ["config", "resources"]
  :source-paths ["src"]
  ;; If you use HTTP/2 or ALPN, use the java-agent to pull in the correct alpn-boot dependency
  ;; :java-agents [[org.mortbay.jetty.alpn/jetty-alpn-agent "2.0.5"]]
  :profiles {:dev {:source-paths ["src", "dev"]
                   :aliases {"run-dev" ["trampoline" "run" "-m" "notes-api.http.server/run-dev"]}
                   :dependencies [[io.pedestal/pedestal.service-tools "0.5.10"]]}
             :uberjar {:aot [notes-api.http.server]}
             :prod {:dependencies [[io.pedestal/pedestal.service-tools "0.5.10"]]
                    :aliases {"run-dev" ["trampoline" "run" "-m" "notes-api.http.server/run-dev"]}}}
  :main ^{:skip-aot true} notes-api.http.server)
