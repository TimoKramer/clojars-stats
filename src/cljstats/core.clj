(ns cljstats.core
    (:require [clojure.edn :as edn]
              [java-time :as jtime]))

(def now (jtime/local-date))

(defn yesterday [] (jtime/minus now (jtime/days 1)))

(defn last-n-days [days] (take days (jtime/iterate jtime/minus (yesterday) (jtime/days 1))))

(defn days-since [date] (Math/abs (jtime/time-between (jtime/local-date) (jtime/local-date "yyyyMMdd" date) :days)))

(defn format-date [local-date] (jtime/format "yyyyMMdd" local-date))

(defn last-dates [days] (map format-date (last-n-days days)))

(defn read-stats [date] (edn/read-string (slurp (str "https://clojars.org/stats/downloads-" date ".edn"))))

(defn hashmapize [stats] (into (hash-map) (map (fn [tuple] {(key tuple) (reduce + (vals (val tuple)))}) stats)))

(defn sort-stats-descending [stats] (sort-by second > stats))

(defn sort-stats-ascending [stats] (sort-by second < stats))

(defn get-stats [date] (hashmapize (read-stats (format-date date))))

(defn get-stats-of-last-days [days] (let [stats (map get-stats days)]
                                      (apply merge-with + stats)))

(defn keywordize [date, stats] (into (hash-map) {(keyword (format-date date)) stats}))

(defn get-raw-stats [date] (keywordize date (read-stats (format-date date))))

(defn get-raw-stats-of-last-days [days] (map get-raw-stats days))

(defn append-to-file-since [date] (spit "cljstats.db.edn" (pr-str (get-raw-stats-of-last-days (last-n-days (days-since date)))) :append true))

