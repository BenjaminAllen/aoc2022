(ns aoc2022.day7 
  (:require [clojure.string :as str]
            [aoc2022.helpers :refer [get-input-lines]]))


(defn handle-cd [fs dir]
  (case dir
    "/" (assoc fs :current-dir ["/"])
    ".." (update fs :current-dir #(drop 1 %))
    (update fs :current-dir #(concat [dir] %))))


;; Why am I like this?
(defn parse-line [fs line]
  (let [[a b] (str/split (str/replace line "$ " "") #" ")] 
    (case a
      "ls"
      fs

      "cd"
      (handle-cd fs b)

      "dir"
      (update-in fs (reverse (:current-dir fs)) #(if (= % nil) {:size 0 %2 {}} (assoc % %2 {:size 0})) b)

      (loop [fs fs
             dir (reverse (:current-dir fs))
             size (Integer/parseInt a)]
        (if (empty? dir)
             fs
             (recur (update-in fs (concat dir [:size]) #(+ % size)) (drop-last 1 dir) size))))))

(defn get-dir-sizes [dir]
  (let [[_ data] dir
        size (:size data)
        dirs (dissoc data :size)]
    (if (empty? dirs)
      size
      (conj (flatten (map get-dir-sizes (seq dirs))) size))))

(def filesystem
  (let [fs {:current-dir ["/"]
            "/" {:size 0}}
        input (get-input-lines "day7")]
    (reduce parse-line fs input)))

(defn part-one []
  (let [fs (seq (dissoc filesystem :current-dir))
        filter-f #(>= 100000 %)]
    (->> (filter filter-f (flatten (map get-dir-sizes fs)))
         (sort >)
         (apply +))))

(defn part-two []
  (let [fs (seq (dissoc filesystem :current-dir))
        available-space (- 70000000 (get-in filesystem ["/" :size]))
        required-space (- 30000000 available-space)
        filter-f #(< required-space %)]
    (->> (filter filter-f (flatten (map get-dir-sizes fs)))
         (sort <)
         first)))

(comment 
  (part-one)
  (part-two)
  )