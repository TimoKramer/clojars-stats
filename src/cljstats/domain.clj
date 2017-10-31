(ns cljstats.domain
  (:require [schema.core :as s]
            [clojure.edn :as edn]
            [java-time :as jtime]
            [ring.swagger.schema :refer [coerce!]]))

;; Domain

;;(s/defschema Charts {:days Integer})

;; Repository

(def now (jtime/local-date))

(defn yesterday [] (jtime/minus now (jtime/days 1)))

(defn last-n-days [days] (take days (jtime/iterate jtime/minus (yesterday) (jtime/days 1))))

(defn format-date [local-date] (jtime/format "yyyyMMdd" local-date))

(defn read-stats [date] (edn/read-string (slurp (str "https://clojars.org/stats/downloads-" date ".edn"))))

(defn hashmapize [stats] (into (hash-map) (map (fn [tuple] {(key tuple) (reduce + (vals (val tuple)))}) stats)))

(defn sort-stats-descending [stats] (sort-by second > stats))

(defn sort-stats-ascending [stats] (sort-by second < stats))

(defn get-stats [date] (hashmapize (read-stats (format-date date))))

(defn get-stats-of-last-days [days] (let [stats (map get-stats days)]
                                      (apply merge-with + stats)))

