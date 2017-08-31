(defproject cljstats "0.1.0-SNAPSHOT"
  :description "wrangling stats from clojars.org"
  :url "https://github.com/timokramer/cljstats"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clojure.java-time "0.3.0"]]
  :main ^:skip-aot cljstats.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
