(ns testlj.core-spec
  (:require [speclj.core :refer :all]
            [testlj.core :refer :all]))

(declare ^:dynamic delayed-true)
(def ^:dynamic dynamic-sequence (atom nil))

(describe "looper"
  (around [it]
    (binding [delayed-true (delay true)]
      (reset! dynamic-sequence [true true false])
      (it)))

  (defn true-once []
    (if (realized? delayed-true) false @delayed-true))

  (defn return-sequence []
    (let [x (first @dynamic-sequence)]
      (reset! dynamic-sequence (rest @dynamic-sequence))
      x))

  (it "should print twice"
    (with-redefs [should-run? return-sequence]
      (should= "looploop" (with-out-str (looper)))))

  (it "should print only once"
    (with-redefs [should-run? true-once]
      (should= "loop" (with-out-str (looper))))))
