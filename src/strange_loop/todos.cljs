(ns strange-loop.todos
 (:require
  [sablono.core :as sab]
  [strange-loop.music :as music :refer [spy]]
  [strange-loop.react-util :as rut]  
  [clojure.string :as string])
 (:require-macros
  [cljs.test :refer [is]]
  [devcards.core :as dc :refer [defcard defcard-doc deftest]]))

;; what is devcards

(defcard
  "## What is Devcards?
   * an attempt to create a lab space for ClojureScript and React   
   * ClojureScript library
   * development application that interactively renders **cards** arranged by namespace
   * revolves around the `defcard` macro
")

#_(defcard
  "### The `defcard` macro raises values from the source code
   ### into the **devcards interface** ")

#_(defcard can-have-an-identifier
  {:devcards {:dispatches "on type " :key 1}})

#_(defcard renders-react-elements
  (sab/html [:div "This is a React Element"]))

;; the mighty todo list

(def sample-todos
  [{:id 1 :content "buy bread"}
   {:id 2 :content "buy music" }
   {:id 3 :content "wash the car" :completed true}
   {:id 4 :content "wash the car again" :completed true}
   {:id 5 :content "take Jenny to hockey practice"}])

;; first lets look at some docs to get a feel for what devcards is

#_(defcard-doc
  "# Let's create a todo <strike>application</strike> item
   Here is the shape of the data:"
  (dc/mkdn-pprint-str sample-todos))

;; devcards dispatch on type

;; explain the flow of whats happening, show the namespace navigation

;; .todo-item.todo-item-completed

(declare complete-todo un-complete-todo)

(defn todo-item [state {:keys [content completed id]}]
  (when-not false #_(string/blank? content)
    (sab/html
     [:div
      {:className
       (str "todo-item"
            #_(when completed
              " todo-item-completed"))}
      content
      #_(if-not false #_completed
                (sab/html [:button.button-link.done.right
                           {:onClick #(do
                                        (music/play-melody)
                                        (swap! state complete-todo id))}
                           "done"])
                #_(sab/html [:button.button-link.not-done.right
                             {:onClick #(swap! state complete-todo id)}
                             "not done"]))])))

;; - card showing regular todo

#_(defcard todo-item
  "This is a regular todo item that hasn't been completed"
  (todo-item nil (first sample-todos)))

;; so this is a LAB SPACE
;; meets needs of a visual repl

;; todo item have different states

;; WHAT WE USUALLY DO is work on this in place and
;; grow it into a full app

;; - completed-todo-item

#_(defcard todo-completed
  "A completed todo should have a line through it."
  (todo-item nil (sample-todos 2)))

;; sometimes bad state
;; - blank todo-item

#_(defcard blank-todos
  "There are three blank todos here.

  You should see nothing below this line:"
  (sab/html
   [:div
    (todo-item nil {})
    (todo-item nil {:content ""})
    (todo-item nil {:content "    "})]))

;; - todo-item that is too long
;; {:content (take 6 (repeat "buy lots of milk"))}

#_(defcard long-todos
  (sab/html
   [:div
    (todo-item nil {:content (take 6 (repeat "buy lots of milk"))})
    (todo-item nil {:content (take 10 (repeat "buy lots of milk"))})
    (todo-item nil {:content (take 20 (repeat "buy lots of milk"))})
    ]))

#_(defcard todolist-example
  (sab/html [:div (map (partial todo-item nil) sample-todos)]))

;; you can also remove the cards interactively as well

;;  show state transitions 

(defn update-todo-item [state id f]
  (mapv #(if (= (:idddd %) id) (f %) %) state))

(defn complete-todo [state id]
  (update-todo-item state id #(assoc % :completed true)))

(defn un-complete-todo [state id]
  (update-todo-item state id #(dissoc % :completed)))

;; - code into a defcard

#_(defcard checking-something (complete-todo sample-todos 1))

;;; - create some tests

#_(deftest updating-todos-test
  (is (-> (update-todo-item sample-todos 1 #(assoc % :hey true))
        first
        :hey))
  #_"`complete-todo` should complete a todo:"
  (is (-> (complete-todo sample-todos 1)
        first
        :completed))
  (is (-> (un-complete-todo sample-todos 3)
        (nth 2)
        :completed
        not)))

#_(cljs.test/run-tests 'strange-loop.todos)

;; we need a way of completing todo items

;; - add a button to allow todo items to be completed

#_(defcard button-and-state-change
  (fn [state _]
    (todo-item state (first @state)))
  ;; initial-data
  sample-todos
  ;; devcards options
  {:inspect-data true
   :history true})


;; - work on css for long todo



;; - create todo item list

;; add dull music

;; goto music and spice it up with card assitence

