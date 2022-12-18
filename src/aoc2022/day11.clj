(ns aoc2022.day11 
  (:require [clojure.string :as str]
            [aoc2022.helpers :refer [extract-number extract-numbers]]))

(def example-input "Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1")

(def example-monkey (first (str/split example-input #"\n\n")))

(defn parse-operation [operation]
  (let [[_ op val] (re-matches #"  Operation: new = old ([*+]) (.+)" operation)
        op (if (= op "*") * +)]
    (if (= val "old")
      (fn [old] (op old old))
      (fn [old] (op old (Integer/parseInt val))))))

(defn parse-test [test t f]
  (let [d (extract-number test)
        t (extract-number t)
        f (extract-number f)]
    (fn [worry]
      (if (zero? (mod worry d)) t f))))

(defn parse-monkey [monkey]
  (let [[name items operation test t f] (str/split-lines monkey)]
    [(extract-number name) {:items (extract-numbers items)
                            :operation (parse-operation operation)
                            :test (parse-test test t f)}]))

(defn parse-monkeys [monkeys]
  (reduce #(let [[k v] (parse-monkey %2)] (assoc % k v)) {} monkeys))

(parse-monkeys (str/split example-input #"\n\n"))

(defn inspect-item [operation item]
  (operation item))

(defn relief [worry]
  (quot worry 3))

(def pm (parse-monkey example-monkey))

(update-in {0 {:items [1]}} [0 :items] #(conj % 3))

(keys {0 {:items []} 1 {:items [2]} 2 {:items [1]}})

((parse-operation "  Operation: new = old * 2") 4)

(mod 17 16)
(re-matches #"Test: divisible by (\d+)" "Test: divisible by 16")
