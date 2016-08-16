(ns constraintriangles.svg)


(defn svg [x y w h & args]
  "Wrapper for svg elements. Returns a hiccup-style svg"
  (into [] (concat [:svg {:viewBox "0 0 1200 1200"}] args)))

(defn svg-line [x1 y1 x2 y2]
  [:line {:x1 x1 :y1 y1
          :x2 x2 :y2 y2
          :stroke "grey"
          :stroke-width 1}])

(defn svg-rect [x y w h]
  "Returns a hiccup-style svg rectangle"
  [:rect {:x x :y y :width w :height h
          :fill "none" :stroke "grey" :stroke-width 1}])

(defn svg-grid [x y w h s]
  "Retuens a grid with squares each s(tep) pixels"
  (into [:g] (concat
              [(svg-rect x y w h)] ; rect
              (for [i (range 0 (/ w s))] ; cols
                (svg-line (+ x (* s i)) y (+ x (* s i)) (+ y h)))
              (for [j (range 0 (/ h s))] ; rows
                (svg-line x (+ y (* s j)) (+ x w) (+ y (* s j)))))))

(defn svg-arc [[x1 y1] [r1 r2] xrot [large? sweep?] [x2 y2]]
  "Returns a hiccup-style svg arc"
  [:path {:d (str "M" x1 "," y1
                  "A" r1 "," r2 " " xrot " " large? "," sweep? " " x2 "," y2)
          :fill "none" :stroke "red" :stroke-width 5}])
