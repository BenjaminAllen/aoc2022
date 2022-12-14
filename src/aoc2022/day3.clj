(ns aoc2022.day3
  (:require [clojure.set :refer [intersection]]
            [aoc2022.helpers :refer [get-input-lines sum split-in-half]]))

(def priorities
  (zipmap "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" (range 1 53)))

(def inventory
  (let [xf (map #(map priorities %))]
    (into () xf (get-input-lines "day3"))))

(defn calculate [items]
  (->> (apply intersection items)
       sum))

(defn get-priorities-for-rucksack [rucksack]
    (->> (split-in-half rucksack)
         (map set)
         calculate))

(defn find-badge-priority [& rucksacks]       
  (-> (flatten rucksacks)
      calculate))

(defn part-one [inventory]
  (->> (map get-priorities-for-rucksack inventory)
       sum))

(defn part-two [inventory] 
  (->> (map set inventory)
       (partition 3)
       (map find-badge-priority)
       sum))

(comment
  (part-one inventory)

  (part-two inventory)
)