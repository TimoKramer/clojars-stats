(defproject cljstats "1.0.1"
  :description "wrangling stats from clojars.org"
  :url  "https://github.com/timokramer/cljstats"
  :license  {:name  "Eclipse Public License"
             :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [metosin/compojure-api "1.1.11"]
                 [clojure.java-time "0.3.0"]]
;;  :ring {:handler compojure.api.examples.handler/app}
  :ring {:handler cljstats.handler/app}
  :uberjar-name "cljstats.jar"
  :uberwar-name "cljstats.war"
  :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]]
                   :plugins [[lein-ring "0.12.0"]]}})
