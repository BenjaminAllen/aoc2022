(ns aoc2022.day3
  (:require [clojure.set :refer [intersection]]
            [aoc2022.helpers :refer [get-input-lines]]
            [clojure.string :as str]))

(def priorities
  (zipmap "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" (range 1 53)))

(def inventory (get-input-lines "day3"))

(defn get-priorities-for-rucksack [rucksack]
  (let [rucksack (map priorities rucksack)
        pockets (partition (/ (count rucksack) 2) rucksack)]
    (->> (map set pockets)
         (apply intersection)
         (reduce +))))

(defn find-badge-priority [& rucksacks]
  (let [rucksacks (flatten rucksacks)]
    (->> (map #(map priorities %) rucksacks)
         (map set)
         (apply intersection)
         (reduce +))))

(defn part-one [inventory]
  (->> (map get-priorities-for-rucksack inventory)
       (reduce +)))

(defn part-two [inventory]
  (->> (partition 3 inventory)
       (map find-badge-priority)
       (reduce +)))

(comment
  (part-one inventory)

  (part-two inventory))