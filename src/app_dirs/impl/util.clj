(ns app-dirs.impl.util
  (:require [clojure.string :as str])
  (:import (clojure.lang IFn)
           #_(java.util Random)))

#_
(defonce ^:dynamic *random-tag*
  (->> (Random.) .nextLong (format "%016x")))

(defn os
  []
  (let [n (-> (System/getProperty "os.name")
            str/lower-case
            str/trim)
        ? (fn [s]
            (->> s (.indexOf n) neg? not))]
    (cond
      (? "mac") :macos
      (? "linux") :linux
      (? "windos") :windows)))

(defn apply-some
  [^IFn f & args]
  (when (every? some? args)
    (apply f args)))
