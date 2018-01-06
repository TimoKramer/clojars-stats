(ns cljstats.db
  (:require [clojure.edn :as edn]
            [java-time :as jtime]))

(def now (jtime/local-date))

(defn yesterday [] (jtime/minus now (jtime/days 1)))

(defn last-dates [from how-many-days] (take how-many-days (jtime/iterate jtime/minus from (jtime/days 1))))

(defn format-date
  "returning the provided LocalDate as String in yyyyMMdd"
  [local-date] (jtime/format "yyyyMMdd" local-date))

(defn days-since
  "returning the full days between date as string and date as LocalDate"
  [since to-date] (Math/abs (jtime/time-between to-date (jtime/local-date "yyyyMMdd" since) :days)))

(defn download-stats
  "given a date as yyyyMMdd-string, returns nil if file already exists or downloads it"
  [date]
  (let [stats-file (clojure.java.io/file (str "stats/downloads-" date ".edn"))]
    (if (.exists stats-file)
      nil
      (try
        (if (spit stats-file (slurp (str "https://clojars.org/stats/downloads-" date ".edn"))) false true)
        (catch Exception e (str "exception: " (.getMessage e)))))))

(defn download-stats-of-last-days [days] (map download-stats days))

(defn read-stats [formatted-date]
  (download-stats formatted-date)
  (edn/read-string (slurp (str "stats/downloads-" formatted-date ".edn"))))

(defn hashmapize [stats] (into (hash-map) (map (fn [tuple] {(key tuple) (reduce + (vals (val tuple)))}) stats)))

(defn load-database []
  (read-stats (format-date (yesterday))))

(defn get-stats [formatted-date] (hashmapize (read-stats formatted-date)))

(defn get-stats-of-last-days [days] (let [dates (map format-date (last-dates days))
                                          stats (map get-stats dates)]
                                      (apply merge-with + stats)))

(defn get-stats-of-last-days-for-libs [days libs]
  (let [stats (get-stats-of-last-days days)]
    (filter #(= (second (key %)) (first libs)) stats)))
