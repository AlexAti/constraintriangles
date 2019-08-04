(ns constraintriangles.geometry
  (:require [clojure.core.logic :as logic :refer []]
            [clojure.core.logic.fd :as fd :refer []]))


(defn canvas
  ([c] (fresh [x y w h] (canvas c x y w h)))
  ([c w h] (fresh [x y] (canvas c x y w h)))
  ([c x y w h]
   (featurec c :x x)
   (featurec c :y y)
   (featurec c :w w)
   (featurec c :h h)
   (featurec c :x2 (+ x w))
   (featurec x :y2 (+ y h))))

(defn point
  ([p] (fresh [x y] (point p x y)))
  ([p x y]
   (featurec p :x x)
   (featurec p :y y)))

(defn painted-point [c p]
  (fresh [x y]
    (point p x y)
    (canvas c)
    (< (:x c) x (:x2 c))
    (< (:y c) y (:y2 c))))

(defn canvas---o-masbien-punto [x y w h]
  ; a esto deberiamos meterle tambien el punto para que instancie un posible punto cualquiera
  [(fd/domain x (+ x w))
   (fd/domain y (+ y h))])


(defn arc []
  "")
