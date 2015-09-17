(ns strange-loop.chart
  (:require
   [sablono.core :as sab]
   [strange-loop.react-util :as rut]
   [cljsjs.c3])
  (:require-macros
   [devcards.core :as dc :refer [defcard defcard-doc deftest]]
   [cljs-react-reload.core :refer [defonce-react-class def-react-class]]))

(defn l [x]
  (prn x) x)

(defcard
  "## Let's work on some charting")

(defonce-react-class Chart
  #js {
       :componentDidUpdate
       (fn [pprops,pstate]
         (this-as
          this
          (.load (aget (.. this -state) "chart")
                 (clj->js
                  (:data (aget (. this -props) "data_"))))))
       
       :componentDidMount
       (fn []
         (this-as
          this
          (let [chart (.generate
                       js/c3
                       (clj->js
                        (merge
                         {:bindto (rut/get-ref this "chart")}
                         (aget (. this -props) "data_"))
                        ))]
            (.setState this #js {:chart chart}))))
       :render
       (fn []
         (this-as this
                  (sab/html [:div 
                             {:ref "chart"}])))})

(defn chart [data]
  (js/React.createElement Chart
                          #js {:data_
                               data}))

(defn simple-line [list-of-numbers]
  (chart {:data {:columns [(concat ["data"] list-of-numbers)]}}))

(defn simple-xy [list-of-pairs]
  (chart {:data
          {:x "x"
           :columns [(concat ["x"] (l (:accum (reduce
                                    (fn [{:keys [total accum]} v]
                                      {:total (+ v total)
                                       :accum (conj accum (+ v total))}
                                      )
                                    {:total 0
                                     :accum []}
                                    (map last list-of-pairs)))))
                     (concat ["data1"] (map first list-of-pairs))]}}))




(defcard line-chart
  (chart
   {:data {:columns [["music" 30, 200, 100, 400, 150, 250]]}}))


(defcard simple-line-chart
  (simple-line [30, 200, 100, 400, 150, 250]))
