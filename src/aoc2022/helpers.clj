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
  (-> (filter pred coll)
      count))