(ns aoc2022.day10
  (:require [aoc2022.helpers :refer [get-input-lines]]
            [clojure.string :as str]))

(def example-input "addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop")

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

(as-> (partition 20 cycles) cycles
  (map last cycles)
  (take-nth 2 cycles)
  (map * cycles (iterate #(+ 40 %) 20))
  (apply + cycles))

(defn pixel [sprite crt]
  (let [crt (mod crt 40)
        distance (abs (- crt sprite))]
    (if (<= -1 distance 1) "#" ".")))

(def pixels (map pixel cycles (range 0 240)))

(->> (partition 40 pixels) 
     (map #( apply str %)))

(pixel 4 46)

(<= -1 -2 1)
(- 30 31)

(take 4 (iterate #(+ 40 %) 20))

(mod 81 40)
(last (range 0 241))