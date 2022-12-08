(ns aoc2022.day8
  (:require [clojure.string :as str]
            [aoc2022.helpers :refer [get-input-lines count-where sum]]))

(defn parse-line [line]
  (map #(Integer/parseInt %) (str/split line #"")))

(defn check-height [acc tree] 
  (let [tallest (acc :tallest)]
    (if (< tallest tree)
      (-> acc 
          (assoc :tallest tree)
          (update :line #(conj % true)))
      (update acc :line #(conj % nil)))))

(defn find-visible [line]
  (let [f #(reduce check-height {:tallest -1 :line []} %)
        forwards (:line (f line))
        backwards (:line (f (reverse line)))
        combined (map vector forwards (reverse backwards))]
    (map #(some identity % ) combined)))

(defn rotate [forest]
  (apply map vector forest))

(defn combine-lines [lines]
  (let [[horizontal vertical] lines]
    (->> (map vector horizontal vertical)
         (map #(some identity %)))))

(defn find-visible-trees [lines] 
  (let [horizontal (map find-visible lines)
        vertical (map find-visible (rotate lines))
        combined (map vector horizontal (rotate vertical))]
    (map combine-lines combined)))

(defn count-visible-trees-in-line [line]
  (count-where identity line))

(defn count-visible-trees [visible-trees]
  (-> (map count-visible-trees-in-line visible-trees) 
      sum))

(defn part-one []
  (let [input (->> (get-input-lines "day8")
                   (map parse-line))]
    (-> input
        find-visible-trees
        count-visible-trees)))



(part-one)
  

