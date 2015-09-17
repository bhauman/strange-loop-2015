(ns strange-loop.core
  (:require
   #_[om.core :as om :include-macros true]
   [sablono.core :as sab :include-macros true]
   [strange-loop.todos]
   [strange-loop.todos-whole]   
   [strange-loop.chart]
   [goog.ui.ac :as ac]
   [goog.events :as gevents])
  (:require-macros
   [devcards.core :as dc :refer [defcard deftest]]
   [cljs-react-reload.core :refer [defonce-react-class def-react-class]]))

(enable-console-print!)

(defcard
  (str
   "## Literate Interactive Coding: Devcards
###<strong>Bruce Hauman</strong>

<center>
Figwheel author </br>
github.com/bhauman </br>
@bhauman </br>
rigsomelight.com </br>
</center>
"))

(defcard
  (str
   "## Context\n"
   "* ClojureScript\n"
   "* Single Page Application development (in browser)\n"
   "* Figwheel and live development\n"))

(defcard
  (str "## Problem\n"
       #_"* code, *reload*, manipulate, **verify** cycle\n"
       #_"* **UI coding** = *endless tweaking* (highly iterative, increases cost of cycle)\n"
       #_"## Solution\n"
       #_"* hot code reloading\n"
       #_"* just write reloadable code\n"
       #_"* React.js, and functional coding together make this much easier\n"
       #_"* figwheel experience report: **people love it** + *high ROI*\n"
       #_"* instantaneous feedback feels **human**, like carving\n"))

#_(defcard
  (str "## Whole file reloading vs. REPL\n"
       "* cuts down cognitive load\n"
       "* simple editor integration (save == eval)\n "))

(defcard
  (str
   "## *Fluid* **Conversational** Programming\n"
   "* the goal is to remove interupts and keep the conversation going\n"
   "## Experiential"))

(defcard
  (str
   "## Problem\n"
   "* We mostly develop against the **Main Application**\n"
   "## Good\n"
   "* for integration\n"
   "## Bad\n"
   "* for displaying multiple states simultaneously\n"
   "* lots of baggage \n"
   "* hides environmental coupling\n"
   "* *poor lab space* <br/>(unlikely to try something new, in a house of cards)\n"))

(defcard
  (str
   "## Solution: create a development application

### Goals

* significantly reduce cost of displaying **independent** code examples
* reduce cost of trying something new that has no place in the main application
* create a space to hold and catalog code examples - dev docs
* create a general **lab space**

### Something like

* a **test suite** is a development application
* a **kitchen sink** is a development application
* a **css style guide** is a development application
"))

#_(defcard (str
  "## Development application is:

* more general
* a lab space
* meta application"))


(defcard (str "##Demo
*<a href=\"/devcards.html#!/strange_loop.todos\">demo</a> 
"))

(defcard
  ""
  "# Advantages of a Development Application for UIs

* **Flexible dev environment** which has more freedom to enumerate different behaviors and play with the state you supply to your code  

* **Validation document** now a QA person or a developer has a document to refer to verify that many different behaviors are working as we expect them to.

* **reference document** much the same way that tests help demonstrate how certain function calls and components are meant to be used, this document would contain numerous code examples that effectively communicate how the code was intended to be used.
")

(defcard
  ""
"# Literate Programming

* We are now displaying literate program documents **in the enviroment** where the program is intended to run!
* running code examples are a glaring ommission
* why are we not doing this?
* well you probably don't want to create code examples in comments that are being run through a preprocessor
* sounds like fun eh?

"
  )











#_(defcard
  "
# Fun Driven Developement

# Context 

* ClojureScript
* SPAs - single page applications in the browser
* Figwheel and live development
* Pragmatism - what can we do now with the tools we have?





# Why is it soooo amazing?

* tightens the feedback loop
* it does this while maintaining state

# Can not evaluate from armchair!

It is important to remember that you cannot evaluate the experience of
live developement of UIs from your armchair. 

It is an experience!

Live developing UIs in ClojureScript is a potent fun experience.

Figwheel has moved from being an interesting idea to a Defacto way of
developing UIs in ClojureScript. This has happened without drum
beating or a PR campaign. 

People like/love it because it is a fun way to work on the endless tweaking
that is UI work.

# Short comings of the REPL

The repl doesn't allow you to see the behavior of the GUI code you are working on.

# Live development works! Whats next?

# Methodology and process

* Test first (selenium) is an absolutely horrible experience UI developement
 - tons of process and in the end offers very little value (tests are brittle, and UIs are dynamic)

* work on your code in the context of the full application

This is what we do. We start the application we are working on in
local development environment and start hacking awy until it looks right.

This methodology makes sense for several reasons: 

1. We derive a tremendous amount of confidence when we see code
running in the context that it is intended to run in. Maybe too much
... These environments are complected and cmoplex and its very helpful
to iterate in this eveironment to tease out strange edge cases that
crop up.

2. There is a cost to creating a seperate environment for the code you
are currently working on. How long does it take to set up a
developement environment?  How do you organize it?



# Analogy: CSS Style guides

Very popular and helpful for projects

What is it?

Its a single separate page where you work on the CSS for your site.

The page as a development environment where you can create
new styles and verify how they interact with the rest of the styles. 

The page also serves as a verfication page that can be looked at on
different devices and browsers to verify that these styles work in all
the environments.

The page also serves as a reference for developers so the can see how
the various css rules are intended to be used.

This is **not automated**. This is an admission and a concession that
we can't automate the checking visual behavior. We need to facilitate
and help humans understand and work with this visual behavior.

# GUIs are visual behavior - humans have to check it

# Code exists in many contexts throughout an application.

See what I'm getting at?

# Free yourself from the tryranny of the main application 


I'm proposing that we have something analogous to a CSS Style Guide
but for your Code.

I'm proposing that it be live to speed up iteration.

This can be done today without any tools the same way that you write a CSS Style Guide.

Create a separate application and add your display components to it in
order. 

Ennumerate your components in their diferent states down the page.

Add comments in HTML explaining how and what type of behavior you are expecting.

This would serve the same purpose.

1. a dev environment, but this dev environment would be different in
   that you have more freedom to enumerate different behaviors and play
   with the state supplied to I.E. you can have the same form
   enumerated in different states down the page.

2. validation document: now a QA person or a developer has a document
   to refer to verify that many different behaviors are working as we
   expect them to.

3. reference document: much the same way that tests help demonstrate
   how certain function calls and components are meant to be used,
   this document would contain numerous code examples that effectively
   communicate how the code was intended to be used.

Analogy: Kitchen Sink application for various UI frameworks

But I'm hinting that these should have more priority and less




# Problem: low adoption rate

This idea makes sense.  Who is going to do it? ...

There is a need to make this way of developing frictionless and easier
than initially coding the main application.


# Proof of concept: Devcards

From this point of view it is important to have General Meta Application that 
trancends the specifics of a main application.

This is analogous to a REPL. The REPL is a general computing
environment, it is a meta application that facilitates the testing and
verification of code behavior. We don't have a visual for this.
IPython notebook comes closest. But its not intended for validating
code running in the environment of the browser.

Devcards aims to be an example of such a general environment for
React/ClojureScript development.

Facilitates live programming and allows you to lift code examples into
the browser live from your source file.

# First and foremost a visual REPL

GUI programming takes a tremendous amount of work. It is highly
iterative and exploratory. You don't know how well an interface
concept is going to work until you implement it and look at it. You
can't understand the exponential tree of complexities and edge cases
an interface presents until you start using it and discovering them.

We need tools that facilitate this exploration.

Functional programming keeps things separate, testable and iterable.

Live programming is definitely one of these tools. It speeds up
iteration tremendously.  

Live programming normally takes place in the context of the main
application. You work on your todo item in the context of the Todo
List application. In a live programming situation the main application
is your visual REPL.  It is context of your live feedback.

This is unduely constraining. The main application isn't a blank
canvas and normally has quite a few constraints the most obvious being
that it is normally very difficult to enumerate a bunch of code
examples displaying the code in its various states live as we work on it.

Enumerating a template in all of its states and working on it live can
provide a lot of concurrency in your process. Much the way a test
suite running in the background provides valuable feedback as to
whether you have just broken something.

This constraint is pointing to an obvious solution: much the way the
REPL is a general environment for computation and verification. We
need a general environment to embed graphical examples, seperate from
the main application. A general canvas on which we paint and
experiment.

This canvas is live and interactive! 

This is markedly different than literate programming. The programming
examples are live and you can interact with them in this environment.


Todo item example.

# Secondly a verification document

You can't automatically verify GUI behavior. Deal with it ...

Next best thing? 

Enumerate various live working components of your application in many
different states so that a human can check them (across devices)
easily. More complete than a kitchen sink type of application.

These documents should also provide helpful documentation for QA folk
so that they can check that things are working properly.

This is a great approach. It cuts down on the tedium of having to run
an application through its paces buy enumerating specific examples
that are already in the appropriate states to check specific behavior.

QA folks can request new examples of difficult test cases ...

If your UI components are indeed separate it should be no problem to
supply these examples. 

You can also have partially integrated examples that talk to servers etc.

A document like this could allow programmers to do these types of checks.

# Thirdly, a reference for the programmer

The programmers who are using and working on the codebase would
benefit from this document that enumerates provides working examples
of the code.  

This artifact can indeed be an interactive guide to how to use the code.


# IPython notebook use case

So while this is very helpful for exploring, verifying and documenting
code behavior.  

It is also useful for situations like interactive exploration of Data.

Or other situations where a visual tool is more expressive and
powerful than code.  

Regex explorer, data science, music

Being able to interactively invoke tooks from your codebase can give
us just the expressive edge we need for lots of problem spaces.







# webpages are documents

Webpages are #documents, they also seem to be where we are deploying a lot of code these days.

So while webpages are actually a dynamic execution environment 

It's curious that we have are still producing static literate code documents?














CSS Style guides

Kitchen sinks


















You cannot verify how a GUI is functioning automatically. You may have
a good feeling when all your integration and acceptance tests are
passing but that feeling is just a feeling and has no basis in reality.






When you are working on a User interface there are many things that
are hard or tedious to validate at the REPL.

In a live development environment, where you are working on
presentation and uis there is an honest need for a visual REPl.

A way to lift code examples out of your source code and display them.







Functions that act on data are easy to work with in a REPL but if you
are creating a todo item... Displaying the todo item requires that you
either include it in a main application or you attach it to some DOM
node.

But you just want to **SEE** it and **Verify** that it is working as you
expect it too.  

Further more you would like to run this todo item through its paces.

What is required to do this?

You need an html page to host the app and a node on that page to mount and 

")


(defn add-autocomplete [this]
  (when-let [comp (aget (.-refs this) "autocomplete_input")]
    (let [input (js/React.findDOMNode comp)
          completer (ac/createSimpleAutoComplete
                     (clj->js (aget (.-props this) "data"))
                     input
                     false false)]
      (.setState this #js {:completer completer})
      (gevents/listen
       completer
       ac/AutoComplete.EventType.UPDATE
       (fn [e]
         (let [v (.-value input)]
           (when-let [handler (aget (.. this -props) "change_handler")]
             (handler v))))))))

(defn remove-autocomplete [this]
  (when-let [completer (.. this -state -completer)]
    (.dispose completer)))

(def-react-class AutoComplete
  #js {:componentDidMount
       (fn [] (this-as this (add-autocomplete this)))
       :componentWillUnmount
       (fn [] (this-as this (remove-autocomplete this)))
       :render
       (fn []
         (this-as
          this
          (sab/html
           [:input
            {:ref "autocomplete_input"
             :onChange
             (fn [e]
               (let [v (.. e -target -value)
                     handle (aget (.. this -props) "change_handler")]
                 (and v handle (fn? handle) (handle v))))}])))})

(defn autocomplete [completions on-change]
  (js/React.createElement AutoComplete #js {:data completions :change_handler on-change}))

#_(defcard autocomplete-card
  (autocomplete ["AABBCC"  "AAA" "BBCCDD" "DDEEFF"] (fn [v] (prn "change: " v))))

(defn main []
  ;; conditionally start the app based on wether the #main-app-area
  ;; node is on the page
  (if-let [node (.getElementById js/document "main-app-area")]
    (js/React.render (sab/html [:div "This is working"]) node)))

(main)

;; remember to run lein figwheel and then browse to
;; http://localhost:3449/devcards.html

