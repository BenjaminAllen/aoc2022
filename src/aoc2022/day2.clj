(ns aoc2022.day2
  (:require [aoc2022.helpers :refer [get-input-lines]]))

(def outcomes-part-one {"A X" 4 "A Y" 8 "A Z" 3
                        "B X" 1 "B Y" 5 "B Z" 9
                        "C X" 7 "C Y" 2 "C Z" 6})

(def outcomes-part-two {"A X" 3 "A Y" 4 "A Z" 8
                        "B X" 1 "B Y" 5 "B Z" 9
                        "C X" 2 "C Y" 6 "C Z" 7})

(def guide (get-input-lines "day2"))

(defn total-points [guide outcomes]
  (->> (map outcomes guide)
       (reduce +)))

(defn part-one []
  (total-points guide outcomes-part-one))

(defn part-two []
  (total-points guide outcomes-part-two))



(comment
  (def example-guide ["A Y" "B X" "C Z"])

  example-guide

  (part-one)

  (part-two) 
  
  (total-points example-guide outcomes-part-two)
  )
