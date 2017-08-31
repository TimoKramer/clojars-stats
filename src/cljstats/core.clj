(ns cljstats.core
    (:require [clojure.edn :as edn]
              [java-time :as jtime]))

(def now (jtime/local-date))

(defn yesterday [] (jtime/minus now (jtime/days 1)))

(defn last-five-days [] (take 5 (jtime/iterate jtime/minus now (jtime/days 1))))

(defn format-date [local-date] (jtime/format "yyyyMMdd" local-date))

(defn read-stats [date] (edn/read-string (slurp (str "https://clojars.org/stats/downloads-" date ".edn"))))

(defn vectorize [stats] (map (fn [tuple] (vector (key tuple) (reduce + (vals (val tuple))))) stats))

(defn sort-stats-descending [stats] (sort-by second > stats))

(defn sort-stats-ascending [stats] (sort-by second < stats))