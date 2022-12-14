(ns aoc2022.day1
  (:require [clojure.string :as str]
            [aoc2022.helpers :refer [get-input]]))

(def elves (-> (get-input "day1")
               (str/split #"\n\n")))

(defn calculate-calories-for-elf [elf]
  (->> (str/split-lines elf)
       (map #(Integer/parseInt %))
       (apply +)))

(defn total-calories-amongst-top-elves [elves top]
  (->> (map calculate-calories-for-elf elves)
       (sort >)
       (take top)
       (apply +)))

(defn part1 [] (total-calories-amongst-top-elves elves 1))
(defn part2 [] (total-calories-amongst-top-elves elves 2))

(comment
  (def exampleinput "1000\n2000\n3000\n\n4000\n\n5000\n6000\n\n7000\n8000\n9000\n\n10000")

  (def example-elves (str/split exampleinput #"\n\n"))

  (total-calories-amongst-top-elves example-elves 1)

  (part1)
  (part2)
  )