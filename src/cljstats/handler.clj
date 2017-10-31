(ns cljstats.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [cljstats.domain :refer :all]
            [schema.core :as s]))

(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "cljstats api"
                    :description "Compojure Api sample application"}
             :tags [{:name "charts", :description "clojars charts"}]}}}

    (context "/charts" []
      :tags ["charts"]

      (GET "/" []
        :query-params [x :- Long]
        :summary "Last x days"
        (ok (sort-stats-ascending (get-stats-of-last-days (last-n-days x))))))))
