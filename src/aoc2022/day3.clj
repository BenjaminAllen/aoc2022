(ns aoc2022.day3
  (:require [clojure.set :refer [intersection]]
            [aoc2022.helpers :refer [get-input-lines]]
            [clojure.string :as str]))

(def priorities
  (zipmap "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" (range 1 53)))

(def inventory (get-input-lines "day3"))

(defn split-rucksack-into [n s]
  (let [s (char-array s)]
    (partition (/ (count s) n) s)))

(defn find-invalid [rucksack]
  (let [pockets (split-rucksack-into 2 rucksack)]
    (apply intersection (map set pockets))))

(defn get-priorities-for-rucksack [rucksack]
  (let [invalid (find-invalid rucksack)]
    (cond
      (nil? invalid) 0
      :else (reduce #(+ %1 (priorities %2)) 0 invalid))))

(defn find-badge-priority [& rucksacks]
  (let [rucksacks (map set (flatten rucksacks))]
    (-> (apply intersection rucksacks)
        first
        priorities)))

(defn part-one [inventory]
  (->> (map get-priorities-for-rucksack inventory)
       (apply +)))

(defn part-two [inventory]
  (->> (map char-array inventory)
       (partition 3)
       (map find-badge-priority)
       (reduce +)))

(comment
  (part-one inventory)

  (part-two inventory))