(defproject strange-loop "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.122"]
                 [devcards "0.2.0-SNAPSHOT"]
                 [sablono "0.3.6"]
                 [sonic-cljs "0.1.0-SNAPSHOT"]
                 [cljs-react-reload "0.1.0"]
                 [cljsjs/c3 "0.4.10-0"]
                 [org.omcljs/om "0.8.8"]
                 #_[reagent "0.5.0"]]

  :plugins [[lein-cljsbuild "1.1.0"]
            [lein-figwheel "0.4.1-SNAPSHOT"]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"]
  
  :source-paths ["src"]

  :cljsbuild {
              :builds [{:id "devcards"
                        :source-paths ["src" #_"../sonic-cljs/src" "../devcards/src"]
                        :figwheel { :devcards true } ;; <- note this
                        :compiler { :main       "strange-loop.core"
                                    :asset-path "js/compiled/devcards_out"
                                    :output-to  "resources/public/js/compiled/strange_loop_devcards.js"
                                    :output-dir "resources/public/js/compiled/devcards_out"
                                   :source-map-timestamp true }}

                       {:id "yome"
                        :source-paths ["src" ]
                        :figwheel true
                        :compiler {:main       "strange-loop.yome"
                                   :asset-path "js/compiled/yome_out"
                                   :output-to  "resources/public/js/compiled/yome.js"
                                   :output-dir "resources/public/js/compiled/yome_out"
                                   :source-map-timestamp true }}

                       #_{:id "todo-dev"
                        :source-paths ["src" ]
                        :figwheel { ;:autoload false
                                   }
                        :compiler {:main       "strange-loop.todos-whole"
                                   :asset-path "js/compiled/whole_out"
                                   :output-to  "resources/public/js/compiled/strange_loop_whole.js"
                                   :output-dir "resources/public/js/compiled/whole_out"
                                   :source-map-timestamp true }}
                       {:id "prod"
                        :source-paths ["src"]
                        :compiler {:main       "strange-loop.core"
                                   :asset-path "js/compiled/out"
                                   :output-to  "resources/public/js/compiled/strange_loop.js"
                                   :optimizations :advanced}}]}

  :figwheel { :css-dirs ["resources/public/css"]
              :open-file-command "emacsclient"})

