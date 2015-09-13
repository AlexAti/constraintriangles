(ns constraintriangles.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [goog.events :as events]
              [goog.history.EventType :as EventType]
              [constraintriangles.svg :refer [svg svg-line svg-grid svg-rect svg-arc]])
    (:import goog.History))

;; -------------------------
;; Components

 (defn slider []
   [:input {:type "range"
            ;:value (:freq @sound)
            :min 0 :max 180
            ;:on-change #(update-freq! (.-target.value %))
            }])

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to constraintriangles"]
    [:div [:a {:href "#/about"} "go to about page"]]
    [slider]
    (svg 0 0 1000 500
      (svg-grid 0 0 1000 500 25)
      (svg-arc))])

(defn about-page []
  [:div [:h2 "About constraintriangles"]
   [:div [:a {:href "#/"} "go to the home page"]]])

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
