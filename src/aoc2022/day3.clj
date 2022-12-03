(ns aoc2022.day3
  (:require [clojure.set :refer [intersection]]
            [aoc2022.helpers :refer [get-input-lines]]))

(def priorities
  (zipmap "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" (range 1 53)))

(def inventory
  (let [xf (map (fn [rucksack] (map priorities rucksack)))]
    (into '() xf (get-input-lines "day3"))))

(defn calculate [items]
  (->> (apply intersection items)
       (reduce +)))

(defn get-priorities-for-rucksack [rucksack]
  (let [pockets (partition (/ (count rucksack) 2) rucksack)]
    (->> (map set pockets)
         calculate)))

(defn find-badge-priority [& rucksacks]
  (-> (flatten rucksacks)
      calculate))

(defn part-one [inventory]
  (->> (map get-priorities-for-rucksack inventory)
       (reduce +)))

(defn part-two [inventory] 
  (->> (map set inventory)
       (partition 3)
       (map find-badge-priority)
       (reduce +)))

(comment
  (part-one inventory)

  (part-two inventory)
)