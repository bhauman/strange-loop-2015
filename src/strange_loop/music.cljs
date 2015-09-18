(ns strange-loop.music
  (:require
   [sonic-cljs.core :as sc]
   [sonic-cljs.webaudio :as wa]
   [sonic-cljs.trig :refer [cosr quantize]]
   [sonic-cljs.pitch :as p]
   [sonic-cljs.visual :refer [option-schema-controls]]
   [strange-loop.chart :as chart]
   [sablono.core :as sab]
   [devcards.util.utils :refer [pprint-str]]
   [cljs.core.async :refer [<!]])
  (:require-macros
   [devcards.core :refer [defcard]]
   [cljs.core.async.macros :refer [go]]))

(defonce loading-ivy-audio-piano
  (go
    (def ivy-audio-piano (<! (wa/load-ivy-audio-piano)))
    (def piano-synth (wa/piano ivy-audio-piano))
    (prn "Ivy audio grand loaded")))

(defn spy [x] (do (js/console.log x) x))

(def scale (p/scale-field :G :major-pentatonic))

(defonce main-synth*
  (wa/poly-synth wa/fm-synth
                 {:sustain-level 0.5
                  :decay 0.4
                  :sustain 0.1
                  :release 0.1
                  :amp 0.16
                  :modulation-index 33
                  :harmonicity 3 }
                 {:voices 4}))

(defcard
  "## Creating a melody generator")

#_(defcard
  "We can use this to adjust the FM synth"
  (option-schema-controls main-synth*))

(defn melody-notes [scale root beat]
  (quantize (int
             (cosr beat
                   (cosr beat 3 5 2)
                   (sc/note root)
                   (/ 3 7)))
   scale))

(defn melody-times []
  (* (/ 1 8 ) (rand-nth [2 1 1 1 1 0.5 0.5 0.5 0.5])))

(defn music-state []
  (sc/initial-player-state
   {:synth #_piano-synth main-synth*
    :speed 0.48
    :start-time (+ (sc/current-time* {:speed 0.48}) 0.01)}))

(defn melody-pitches [x]
  (melody-notes scale 62 x))


(defn beats []
  (iterate #(+ % (/ 1 8)) 0))

(defn rand-melody []
  (take (+ 4 (rand-int 8))
        (drop
         (rand-int 50)
         (map
          (fn [x]
            (sc/n (melody-pitches x)
                  (melody-times)
                  (cosr x 0.4 0.6 0.3)))
          (beats)))))

(defn play-melody* [notes]
  (sc/play-music! (music-state)
                   [:+ notes]))

(defn play-melody []
  (play-melody* (rand-melody)))

(defcard melody-chart
  (let [melody (rand-melody)]
    (js/setTimeout #(play-melody* melody) 500)
    (sab/html
     [:div
      (chart/simple-xy (map (juxt :pitch :duration) melody))
      [:pre
       (pprint-str melody)]])))

