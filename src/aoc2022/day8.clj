(ns aoc2022.day8
  (:require [clojure.string :as str]
            [aoc2022.helpers :refer [get-input-lines count-where sum]]))

(def example-input (str/split-lines "30373
25512
65332
33549
35390"))

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

(defn viewing-distance [trees tree]
  (loop [tree tree
         trees trees
         distance 0]
    (cond 
      (empty? trees)
      distance
      
      (>= (first trees) tree)
      (inc distance)
      
      :else
      (recur tree (rest trees) (inc distance)))))


(defn find-viewing-distance [acc tree]
  (let [distance (viewing-distance (acc :trees) tree)]
    (-> acc
        (update :line #(conj % distance))
        (update :trees #(concat [tree] %)))))

(defn find-viewing-distances-for-line [line]
  (let [f #(reduce find-viewing-distance {:line [] :trees []} %)
        forwards (:line (f line))
        backwards (:line (f (reverse line)))
        combined (map vector forwards (reverse backwards))]
    (map #(apply * %) combined)))

(defn combine-distances [line]
  (let [ [horizontal vertical] line]
       (->> (map vector horizontal vertical)
            (map #(apply * %)))))

(defn find-viewing-distances [forest]
  (let [horizontal (map find-viewing-distances-for-line forest)
        vertical (map find-viewing-distances-for-line (rotate forest))
        combined (map vector horizontal (rotate vertical))]
    (->> (map combine-distances combined)
         (map #(apply max %))
         (apply max))))


(defn part-two []
  (find-viewing-distances (map parse-line (get-input-lines "day8"))))

(part-two)

