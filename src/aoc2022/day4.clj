(ns aoc2022.day4 
  (:require [aoc2022.helpers :refer [get-input-lines count-where]]))

(def pattern #"(\d+)-(\d+),(\d+)-(\d+)")

(defn parse [pair]
  (let [[_ a b x y] (re-matches pattern pair)]
    [[(Integer/parseInt a) (Integer/parseInt b)]
     [(Integer/parseInt x) (Integer/parseInt y)]]))

(defn sort-pair [pair] 
  (sort #(compare (first %) (first %2)) pair))

(def pairs 
  (let [xf (comp (map parse)
                 (map sort-pair))] 
    (into [] xf (get-input-lines "day4"))))

(defn fully-contains? [pair]
  (let [[[a b] [x y]] pair]
    (<= a x y b)))

(defn overlapped? [pair]
  (let [[[_ b] [x _]] pair]
    (>= b x)))

(defn part-one []
  (count-where fully-contains? pairs))

(defn part-two []
  (count-where overlapped? pairs))

(comment
  (part-one)
  (part-two)
  )
