(ns strange-loop.todos-whole
 (:require
  [sablono.core :as sab]
  [strange-loop.music :as music :refer [spy]]
  [strange-loop.react-util :as rut]  
  [clojure.string :as string])
 (:require-macros
  [cljs.test :refer [is]]
  [devcards.core :as dc :refer [defcard defcard-doc deftest]]))

(def sample-todos
  [{:id 1 :content "buy bread"}
   {:id 2 :content "buy music" }
   {:id 3 :content "wash the car" :completed true}
   {:id 4 :content "wash the car again" :completed true}
   {:id 5 :content "take Jenny to hockey practice"}])

(declare complete-todo un-complete-todo)

(defn todo-item [state {:keys [content completed id]}]
  (when-not (string/blank? content)
    (sab/html
     [:div
      {:className
       (str "todo-item"
           (when completed
              " todo-item-completed"))}
      content
      (if-not completed
              (sab/html [:button.button-link.done.right
                         {:onClick #(do
                                      (music/play-melody)
                                      (swap! state complete-todo id))}
                         "done"])
              (sab/html [:button.button-link.not-done.right
                         {:onClick #(swap! state un-complete-todo id)}
                         "not done"]))])))

(defn todo-list [state]
  (sab/html [:div (map (partial todo-item state) @state)]))


(defn update-todo-item [state id f]
  (mapv #(if (= (:id %) id) (f %) %) state))

(defn complete-todo [state id]
  (update-todo-item state id #(assoc % :completed true)))

(defn un-complete-todo [state id]
  (update-todo-item state id #(dissoc % :completed)))

(defn create-todo [state content]
  (conj state
        {:id (inc (or (apply max (map :id state)) 0))
         :content content}))

(defn todo-input [state]
  (rut/get-owner
   (fn [owner]
     (sab/html
      [:input.todo-input
       {:ref "todo-input"
        :type "text"
        :placeholder "Thing that needs some doing"
        :onKeyPress (rut/on-enter
                     (fn [e]
                       (swap! state create-todo
                              (rut/ref-value owner "todo-input"))
                       (set! (.-value (rut/get-ref owner "todo-input")) "")
                       true))}]))))

#_(defcard todo-input-comp
  "We need an input field to allow us to add todos"
  (fn [state _]
    (todo-input state))
  []
  {:history true
   :inspect-data true})

(defn clear-completed [todos]
  (vec (filter (complement :completed) todos)))

(defn status-bar [state]
  (when (not-empty @state)
    (sab/html
     [:div.todo-item {:style {:color "#aaa"}}
      [:span (count (filter (complement :completed)
                            @state)) " items left"]
      [:span {:style {:float "right" :paddingRight "0px"}}
       [:button.button-link
        {:onClick #(swap! state clear-completed)}
        " Clear Completed "]]])))

(defn full-todos [state]
  (sab/html
   [:div.todo-app
    [:h1  "Todos"]
    (todo-input state)
    (todo-list state)
    (status-bar state)]))

(defonce app-state (atom []))

(defcard todos
  (fn [app-state]
    (full-todos app-state))
  app-state
  {:frame false})

(defn main []
  (when-let [app-elem (.getElementById js/document "main-app-area")]
    (js/React.render )))

(add-watch app-state :watching-for-changes (fn [_ _ _ _] (main)))

(main)
;; - work on css for long todo



;; - create todo item list

;; add dull music

;; goto music and spice it up with card assitence

