(ns aoc2022.day10
  (:require [aoc2022.helpers :refer [get-input-lines sum]]
            [clojure.string :as str]))

(defn noop [cycles]
  (conj cycles (last cycles)))

(defn addx [cycles amount]
  (let [n (last cycles)]
    (conj (noop cycles) (+ n amount))))

(defn perform-instruction [cycles instruction]
  (let [[_i amount] (str/split instruction #" ")]
    (if (nil? amount)
      (noop cycles)
      (addx cycles (Integer/parseInt amount)))))

(def cycles (reduce perform-instruction [1] (get-input-lines "day10")))

(defn pixel [sprite crt]
  (let [crt (mod crt 40)
        distance (abs (- crt sprite))]
    (if (<= -1 distance 1) "#" ".")))

(defn part-one []
  (let [cycles (partition 20 cycles)]
    (->> cycles
         (map last)
         (take-nth 2)
         (map * (iterate #(+ 40 %) 20))
         sum)))

(defn part-two []
  (let [pixels (map pixel cycles (range 0 240))]
    (->> pixels
         (partition 40)
         (map #(apply str %)))))

(comment
  (part-one)
  (part-two)
  )