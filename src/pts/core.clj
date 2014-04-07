(ns pts.core
  (:require [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]])
  (:import (java.lang Math))
  (:gen-class))

;; pts.clj -- A simple implementation of the Weight Watchers Points Plus algorithm, as
;; described at http://en.wikipedia.org/wiki/Weight_Watchers#PointsPlus_.28US.3B_Nov_2010-.29
;;
;; This code is not affiliated with Weight Watchers International in any way.
;;
;; This code is distributed under the Eclipse Public License. See the LICENSE file,
;; distributed with this code for details.
;;
;; Copyright (c) 2014 - Joey Gibson - joey@joeygibson.com
;;

(def cli-options
  "The list of options given to parse-opts, describing the acceptable command-line options."
  [["-f" "--fat FAT" "Fat grams"
    :default 0
    :parse-fn #(Double/parseDouble %)
    :validate [#(>= % 0) "Fat must be greater than or equal to zero"]]
   ["-c" "--carbs CARBS" "Carbohydrate grams"
    :default 0
    :parse-fn #(Double/parseDouble %)
    :validate [#(>= % 0) "Carbs must be greater than or equal to zero"]]
   ["-b" "--fiber FIBER" "Fiber grams"
    :default 0
    :parse-fn #(Double/parseDouble %)
    :validate [#(>= % 0) "Fiber must be greater than or equal to zero"]]
   ["-p" "--protein PROTEIN" "Protein grams"
    :default 0
    :parse-fn #(Double/parseDouble %)
    :validate [#(>= % 0) "Protein must be greater than or equal to zero"]]
   ["-v" nil "Be verbose"
    :id :verbosity
    :default 0
    :assoc-fn (fn [m k _] (update-in m [k] inc))]
   ["-h" "--help"]])

(defn usage
  "Used to print out a usage summary of the program options."
  [options-summary]
  (->> ["Usage: pts [options]"
        ""
        "Options:"
        options-summary]
       (string/join \newline)))

(defn error-message [errors]
  (str "The following errors occurred:\n\n" (string/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn correct-fiber
  "In the formula, no more than 4 grams of fiber are counted."
  [values]
  (assoc values :fiber (min (:fiber values) 4.0)))

(defn calculate-points
  "The formula itself. The return value is a double that is the number of points."
  [values]
  (let [fat (:fat values)
        carbs (:carbs values)
        fiber (:fiber values)
        protein (:protein values)]
    (if (> (:verbosity values) 0)
      (println (format "Fat: %f, Carbs: %f, Fiber: %f, Protein: %f" fat carbs fiber protein)))
    (-> (* 16 protein)
        (+ (* 19 carbs))
        (+ (* 45 fat))
        (- (* 14 fiber))
        (/ 175.0)
        (Math/round)
        (max 0))))

(defn display-points
  "Print out the points that the formula computed."
  [values]
  (println values))

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) (exit 0 (usage summary))
      (every? #(= % 0) (vals options)) (exit 1 (usage summary))
      errors (exit 1 (error-message errors)))
    (display-points (calculate-points (correct-fiber options)))))

