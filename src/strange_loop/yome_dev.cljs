(ns strange-loop.yome-dev
  (:require
   [om.core :as om :include-macros true]
   [clojure.string :as string]
   [clojure.set :refer [map-invert]]
   [sablono.core :as sab])
  (:require-macros
   [cljs.test :refer [is]]
   [devcards.core :refer [defcard defcard-doc deftest] :as dc]))

(enable-console-print!)

(defcard
  "## Devcards?
   * an attempt to create a lab space for ClojureScript and React   
   * ClojureScript library
   * **development application** that interactively renders **cards** arranged by namespace
   * revolves around the `defcard` macro
")

#_(defcard
  "### The `defcard` macro raises values from the source code
   ### into the **devcards interface**")

#_(defcard can-have-an-identifier
  #_"devcards can also have optional **markdown** documentation"
  {:devcards {:dispatches "on type " :key 1}})

(defn square-range [& args]
  (map (fn [x] (* x x)) (apply range args)))

#_(defcard square-range-output
  (square-range 5 10))

#_(defcard renders-react-elements
  (sab/html
   [:div
    [:p "This is a React Element"]
    #_[:img {:src "images/yome.jpg" :width "100%"}]]))

(def sample-sides
  [{:corner nil, :face :window}
   {:corner :door-frame, :face :window}
   {:corner :stove-vent, :face :window}
   {:corner :zip-door, :face nil}
   {:corner nil, :face nil}
   {:corner nil, :face nil}
   {:corner nil, :face nil}   
   {:corner nil, :face nil}])

(def sample-data
  {:sides sample-sides})

#_(defcard-doc
  "# Let's work on a Yome Widget
  
  Here is the data that we are going to use to model the yome:"
  (dc/mkdn-pprint-str sample-data)
  "Since yomes can have a varying number of sides we are going to 
   represent each side as a combination of a `:corner` and a `:face`

   A `:corner` can take one of the values `[:door-frame :zip-door :stove-vent nil]`

   A `:face` can either be `nil` or `:window`.")

(defn side-count [yome]
  (count (:sides yome)))

(defn yome-theta [yome]
  (/ (* 2 js/Math.PI) (side-count yome)))

(defn yome-deg [yome]
  (/ 360 (side-count yome)))

(defn rotate [theta {:keys [x y] :as point} ]
  (let [sint (js/Math.sin theta)
        cost (js/Math.cos theta)]
    (assoc point
           :x (- (* x cost) (* y sint))
           :y (+ (* x sint) (* y cost)))))

(defn radial-point [radius theta]
  (rotate theta {:x 0 :y radius}))

#_(defcard radial-point
  "Radial points are going to be our fulcrum for drawing these 
   round drawings."
  (radial-point 100 (* js/Math.PI 2)) )

#_(deftest radial-points
  "Should be able to walk a radial point around a circle."
  (is (= (:x (radial-point 100 (* 0.5 js/Math.PI))) -100 ))
  (is (= (:y (radial-point 200 js/Math.PI)) -200 ))
  (is (= (:x (radial-point 300 (* 1.5 js/Math.PI))) 300 ))
  (is (= (:y (radial-point 400 (* 2 js/Math.PI))) 400)))

#_(defn svg-container [content]
  (sab/html
   [:svg {:class "yome" :height 500 :width 500
          :viewBox "-250 -250 500 500"
          :preserveAspectRatio "xMidYMid meet" }
    content]))

(defn small-container [content]
  (sab/html
   [:svg {:class "yome" :height 300 :width 500
          :viewBox "-250 -250 500 500"          
          :preserveAspectRatio "xMidYMid meet" }
    content]))

#_(defcard svg-container
  "We need an svg container and this container should have and we are going
  to abuse some svg features to get an origin in the center.

  This prevents us from needing to do point adjustments all the time.

  You should see a circle rendered in the center of the container below:
  "
  (svg-container
   (sab/html
    [:ellipse {:cx 0 :cy 0 :rx 15 :ry 15}])))

(defmulti draw identity)

(defmethod draw :default [_ _]
  (sab/html [:g]))

(defmethod draw :line [_ {:keys [start end]}]
  (sab/html [:line {:x1 (:x start)
                    :y1 (:y start)
                    :x2 (:x end)
                    :y2 (:y end)}]))

#_(defcard draw-line
  "We can draw lines.  Note that the origin is in the center
   and the positive numbers draw towards the bottom and the right.

   ```
   (draw :line {:start {:x 0 :y 0} :end {:x 200 :y 200}})
   ```"
  (svg-container
   (draw :line {:start {:x 0 :y 0} :end {:x 200 :y 200}})))

;; work on side lines

(defn side-line [radius start-theta end-theta]
  {:start (radial-point radius start-theta)
   :end   (radial-point radius end-theta)
   :type :line})

(defn yome-side-line [radius num-sides]
  (let [theta (/ (* 2 js/Math.PI) num-sides)]
    (side-line radius 0 theta)))

(defmethod draw :side [_ yome]
  (sab/html (draw :line (yome-side-line 180 (side-count yome)))))

#_(defcard draw-side-line
  (svg-container
   (draw :side sample-data)))

;; work on drawing a window

(defn points [p-list]
  (string/join " "  (map (comp #(string/join "," %) (juxt :x :y))
                         p-list)))

(defmethod draw :window [_ yome]
  (let [theta (yome-theta yome)
        indent (/ theta 6) ; 6
        {:keys [start end]}
        (side-line 160 indent (- theta indent))
        mid    (radial-point 100 (/ theta 2))] ; 100
    (sab/html [:polygon { :class "yome-window"
                         :key "yome-window"
                         :points (points (list start end mid))}])))

#_(defcard draw-window
  (svg-container
   (draw :window sample-data)))

(defmethod draw :door-frame [_ yome]
  (let [theta (yome-theta yome)
        indent (* 2.2 (/ theta 6))
        door-top (side-line 165 indent (- theta indent))
        door-bottom (side-line 90 indent (- theta indent))]
    (sab/html [:polygon {:class "yome-door"
               :key "yome-door"
               :points (points (list (:start door-top) (:end door-top)
                                     (:end door-bottom) (:start door-bottom)))
               :transform (str "rotate(-" (/ (yome-deg yome) 2) ", 0, 0)")}])))

(defmethod draw :zip-door [_ yome]
  (let [theta (yome-theta yome)
        indent (* 0.15 (/ theta 6))
        zips (map (fn [x]
                    (side-line (- 170 (* 10 x))
                               indent
                               (- indent)))
                  (range 9))]
    [:g {:class "yome-zip-door"
         :key "yome-zip-door"}
     (map (partial draw :line)
          (cons {:type :line
                 :start (radial-point 180 0)
                 :end   (radial-point 80 0)} zips))]))


(defmethod draw :stove-vent [_ yome]
  (let [theta (yome-theta yome)
        point (radial-point 155 0)]
    [:ellipse {:cx (:x point) :cy (:y point) :rx 14 :ry 8
               :class "yome-stove-vent"
               :key "yome-stove-vent"}]))

(defn yome-side [yome index]
  (let [num-sides (side-count yome)
        {:keys [corner face]} (get-in yome [:sides index])]
    (sab/html [:g {:transform (str "rotate("
                                   (* (yome-deg yome) index)
                                   ", 0, 0)")
                   :class "yome-side"
                   :key (str "yome-side-" index)}
               (cons
                (draw :side yome)
                (map #(draw % yome)
                     (keep identity [corner face])))])))

#_(defcard draw-some-sides
  (svg-container
   (sab/html
    [:g
     (yome-side sample-data 0)
     (yome-side sample-data 1)
     (yome-side sample-data 2)     
     (yome-side sample-data 3)])))

(defn draw-yome [yome]
  (sab/html
   [:g #_{:transform (str "rotate(-" (/ (yome-deg yome) 2) ", 0, 0)")}
    (map (partial yome-side yome) (range (side-count yome)))]))

#_(defcard draw-8-side-yome
  (sab/html
   (svg-container (draw-yome sample-data))))

#_(defcard draw-7-side-yome
  (sab/html
   (svg-container (draw-yome {:sides (vec (take 7 sample-sides))}))))

#_(defcard draw-6-side-yome
  (sab/html
   (svg-container (draw-yome {:sides (vec (take 6 sample-sides))}))))

;; add more windows to the sample data

(defn prevent->value [f]
  (fn [e]
    (.preventDefault e)
    (let [v (.-value (.-target e))]
      (f v))))

(defn change-yome-sides [yome v]
  (assoc yome
         :sides (mapv (fn [i]
                       (if-let [s (get-in yome [:sides i])]
                         s
                         {:corner nil
                          :face nil})) (range v))))

(defn select-yome-size [n state]
  (sab/html
   [:div
       [:select.yome-type-select
        {:value    (side-count @state)
         :onChange (prevent->value
                    (fn [v]
                      (swap! state change-yome-sides v)))}
        (map-indexed
         (fn [i y]
           [:option {:value (+ 6 i)} y])
         ["HexaYome" "SeptaYome" "OctaYome"])]]))



#_(defcard change-yome-sides
  (fn [yome _]
    (sab/html
     [:div
      (select-yome-size nil yome)
      (small-container (draw-yome @yome))]))
  sample-data
  {:inspect-data true
   :history true})



