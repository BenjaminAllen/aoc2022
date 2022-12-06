(ns aoc2022.day5
  (:require [aoc2022.helpers :refer [get-input]]
            [clojure.string :as str]))

(defn parse-crate-row [acc row]
  (let [row (partition 4 4 (cycle "") row)
        stack (zipmap (range 1 (inc ( count row))) 
                      (map #(let [[x c _ _] %]
                              (if (= \[ x)
                                (list c)
                                (list nil)))
                           row))] 
    (merge-with concat acc stack)))

(defn parse-stacks [stacks]
  (let [stacks (drop-last 1 (str/split-lines stacks))]
    (->> (reduce parse-crate-row {} stacks)
         (reduce-kv #(assoc % %2 (remove nil? %3)) {}))))

(defn parse-input [input] 
  (let [[stacks instructions] (str/split input #"\n\n")
        stacks (parse-stacks stacks)
        instructions (str/split-lines instructions)] 
    [stacks instructions]))

(defn parse-instruction [instruction]
  (let [[_ n from to] (re-matches #"move (\d+) from (\d) to (\d)" instruction)]
    [(Integer/parseInt n)
     (Integer/parseInt from)
     (Integer/parseInt to)]))

(defn crate-moving-strategy [f]
  (fn [stacks instruction]
    (let [[n from to] (parse-instruction instruction)]
      (-> (update stacks to #(apply conj % (f (take n (stacks from)))))
          (update from #(drop n %))))))

(defn get-top-crates [stacks] 
  (->> (map #(first (stacks %)) (range 1 (inc (count stacks))))
       (apply str))) 

(defn move-crates [crate-move-strategy]
  (let [[stacks instructions] (parse-input (get-input "day5"))]
    (->> (reduce crate-move-strategy stacks instructions)
         get-top-crates)))

(defn part-one []
  (move-crates (crate-moving-strategy identity)))

(defn part-two []
  (move-crates (crate-moving-strategy reverse)))

(comment
  (part-one)
  (part-two)
  )
