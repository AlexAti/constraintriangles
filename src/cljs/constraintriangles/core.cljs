(ns constraintriangles.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [goog.events :as events]
              [goog.history.EventType :as EventType]
              [constraintriangles.svg :refer [svg svg-line svg-grid svg-rect svg-arc]])
    (:import goog.History))

;; -------------------------
;; State management

(defonce app-state (atom {:radius 400}))

;; -------------------------
;; Components

(defn slider []
  [:input {:type "range"
           :value (:radius @app-state)
           :min 100 :max 1000
           :on-change #(swap! app-state assoc :radius (.-target.value %))}])


(defn label []
  [:p (:radius @app-state)])

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to constraintriangles"]
    [:div [:a {:href "#/about"} "go to about page"]]
    [slider]
    [label]
    (let [r (:radius @app-state)
          [x y] [350 150]
          [w h] [300 160]
          a [(/ (+ x x w) 2) y]
          b [x (+ h y)]
          c [(+ x w) (+ h y)]]
      (svg 0 0 1000 500
        (svg-grid 0 0 1000 500 50)
        (svg-arc a [r r] 0 [0 1] b)
        (svg-arc b [r r] 0 [0 1] c)
        (svg-arc c [r r] 0 [0 1] a)))])

(defn about-page []
  [:div [:h2 "About constraintriangles"]
    [:div [:a {:href "#/"} "go to the home page"]]
    [:div
      [:p "Hello!"]
      [:p "This is a basic experiment with constraint logic in the browser."]
      [:p "You can find the code in "[:a {:href "https://github.com/AlexAti/constraintriangles"} "this repo."]]
      [:p "If you want to dig a little bit deeper, google the following subjects: clojurescript, reagent and core.logic."]]])

(defn current-page []
  [:div [(session/get :current-page)]])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (session/put! :current-page #'home-page))

(secretary/defroute "/about" []
  (session/put! :current-page #'about-page))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (hook-browser-navigation!)
  (mount-root))
