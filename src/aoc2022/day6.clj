(ns aoc2022.day6
  (:require [aoc2022.helpers :refer [get-input]]))

(def input (get-input "day6"))

(defn find-marker [input n]
  (let [sets (->> (partition n 1 input)
                  (map set))]
    (reduce #(if (= n (count %2)) (reduced %) (inc %)) n sets))) 

(defn part-one []
  (find-marker input 4))

(defn part-two []
  (find-marker input 14))

(comment
  (part-one)
  
  (part-two)
  )


