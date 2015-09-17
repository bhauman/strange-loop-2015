(ns strange-loop.react-util
  (:require)
  (:require-macros
   [cljs-react-reload.core :refer [defonce-react-class]]
   [devcards.core :refer [defcard]]))

;; React helpers

(defonce-react-class OwnerComponent
  #js {:render (fn []
                 (this-as this
                          ((.. this -props -childfn) this)))})

(defn get-owner [f]
  (js/React.createElement OwnerComponent #js {:childfn f}))

(defn get-ref [owner ref-key]
  (js/React.findDOMNode (aget (.-refs owner) ref-key)))

(defn ref-value [owner ref-key]
  (.-value (get-ref owner ref-key)))

(defn on-enter [f]
  (fn [e]
    (when (= 13 (.-charCode e))
      (f e))
    true))

