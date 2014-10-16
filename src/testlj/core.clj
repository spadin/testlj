(ns testlj.core)

(defn should-run? [] true)

(defn looper []
  (loop [keep-going? (should-run?)]
    (print "loop")
    (if-let [keep-going? (should-run?)]
      (recur keep-going?))))
