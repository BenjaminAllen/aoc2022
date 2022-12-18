(ns aoc2022.helpers
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))
  
(defn get-input [input]
  (slurp (io/resource (str "inputs/" input))))

(defn get-input-lines [input]
  (let [input (get-input input)]
    (str/split-lines input)))

(defn sum [x]
  (reduce + x))

(defn split-in-half [x]
  (partition (/ (count x) 2) x))

(defn count-where [pred coll]
  (reduce #(if (pred %2) (inc %) %) 0 coll))

(defn extract-numbers
  ([s]
   (let [numbers (re-seq #"\d+" s)]
     (mapv #(Integer/parseInt %) numbers)))
  ([s & n]
   (let [numbers (extract-numbers s)]
     (mapv #(let [n (if (neg-int? %) (+ (count numbers) %) %)] (nth numbers n nil)) n))))

(defn extract-number [s]
  (let [numbers (extract-numbers s)]
    (first numbers)))

