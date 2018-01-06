(ns cljstats.db-test
  (:import [java.time LocalDate])
  (:require [clojure.test :refer :all]
            [cljstats.db :refer :all]
            [java-time :as jtime]))

(deftest yesterday-test
  (testing "yesterday"
    (is (instance? java.time.LocalDate (yesterday)))))

(deftest last-days-test
  (testing "last three days"
    (is (instance? java.time.LocalDate (second (last-dates (yesterday) 3))))
    (is (= (count (last-dates (yesterday) 3)) 3))))

(deftest format-date-test
  (testing "format date of 4th of January 2018"
    (is (= (format-date (jtime/local-date 2018 01 04)) "20180104"))))

(deftest days-since-test
  (testing "get days between specific dates"
    (is (instance? Integer (days-since "20170614" now)))
    (is (= 204 (days-since "20170614" (jtime/local-date 2018 01 04))))))

;;; integration tests
;;(deftest download-stats-test
;;  (testing "check for file from 20170918"
;;    (is (nil? (download-stats "20170918"))))
;;  (testing "check for yesterdays file, expecting a true"
;;    (is (true? (download-stats (format-date (yesterday)))))))
;;
;;(deftest download-stats-of-last-days-test
;;  (testing "loading the last three days"
;;    (is (download-stats-of-last-days 3))))
;;
;;(deftest read-stats-test
;;  (testing "read todays stats from hd"
;;    (is (map? (read-stats "20170918")))))

(deftest load-database-test
  (testing "load data from filesystem"
    (is (set? (load-database)))))


(deftest hashmapize-test
  (testing "returning a hash-map from stats"
    (let [edn {["viz-cljc" "viz-cljc"] {"0.1.2" 2}, ["com.taoensso" "faraday"] {"1.9.0" 7, "1.7.1" 1, "1.6.0" 1}, ["pjstadig" "humane-test-output"] {"0.7.1" 3, "0.8.2" 20, "0.8.3" 63, "0.7.0" 18, "0.6.0" 2287, "0.8.1" 154, "0.8.0" 1}, ["lein-junit" "lein-junit"] {"1.1.7" 2, "1.1.8" 7}, ["cirru" "sepal"] {"0.1.2" 1}}]
      (is (map? (hashmapize edn))))))
