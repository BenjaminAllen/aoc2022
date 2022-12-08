(ns aoc2022.day8
  (:require [clojure.string :as str]
            [aoc2022.helpers :refer [get-input-lines count-where]]))

(defn parse-line [line]
  (map #(Integer/parseInt %) (str/split line #"")))
 
(def forest (->> (get-input-lines "day8")
                 (map parse-line)))

(defn rotate [forest]
  (apply map vector forest))

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

(defn check-height [acc tree] 
  (let [tallest (acc :tallest)]
    (if (< tallest tree)
      (-> acc 
          (assoc :tallest tree)
          (update :line #(conj % true)))
      (update acc :line #(conj % nil)))))

(defn find-viewing-distance [acc tree]
  (let [distance (viewing-distance (acc :trees) tree)]
    (-> acc
        (update :line #(conj % distance))
        (update :trees #(concat [tree] %)))))

(defn calculate-line-f [calculate-f merge-f]
  (fn [line]
    (let [forwards (:line (calculate-f line))
          backwards (:line (calculate-f (reverse line)))
          combined (map vector forwards (reverse backwards))]
      (map merge-f combined))))

(defn combine-f [f]
  (fn [line]
    (let [[horizontal vertical] line]
      (->> (map vector horizontal vertical)
           (map f)))))

(defn calculate-forest [lines calculate-f merge-f result-f]
  (let [calculate-f (calculate-line-f calculate-f merge-f)
        combine-f (combine-f merge-f)
        horizontal (map calculate-f lines)
        vertical (map  calculate-f (rotate lines))
        combined (map vector horizontal (rotate vertical))]
    (as-> (map combine-f combined) forest
      (flatten forest)
      (result-f forest))))

(defn part-one []
  (let [calculate-f #(reduce check-height {:tallest -1 :line []} %)
        merge-f #(some identity %)
        result-f #(count-where true? %)] 
      (calculate-forest forest calculate-f merge-f result-f)))


(defn part-two []
  (let [calculate-f #(reduce find-viewing-distance {:line [] :trees []} %)
        merge-f #(apply * %)
        result-f #(apply max %)]
    (calculate-forest forest calculate-f merge-f result-f)))

(comment
  (part-one) 
  (part-two)
  )

