(ns aoc2022.helpers
  (:require [clojure.java.io :as io]))
  
(defn get-input [input]
  (slurp (io/resource (str "inputs/" input))))

(get-input "day1")