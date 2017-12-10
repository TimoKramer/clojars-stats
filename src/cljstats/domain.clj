(ns cljstats.domain
  (:require [schema.core :as s]
            [cljstats.db :as db]))

;; Domain

;;(s/defschema Charts {:days Integer})

;; Repository

(defn sort-stats-descending [stats] (sort-by second > stats))

(defn sort-stats-ascending [stats] (sort-by second < stats))

(defn load-stats-of-last-days
  ([days] (db/get-stats-of-last-days days))
  ([days & libs] (db/get-stats-of-last-days-for-libs days libs)))
