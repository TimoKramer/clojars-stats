(ns cljstats.db
    (:require [clojure.edn :as edn]
              [cljstats.core :refer :all]))

(defn load-database []
  (read-stats (format-date (yesterday))))
