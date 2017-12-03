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
        :query-params [days :- Long]
        :summary "Last x days"
        (ok (sort-stats-ascending (load-stats-of-last-days days)))))))
