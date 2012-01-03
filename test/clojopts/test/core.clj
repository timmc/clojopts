(ns clojopts.test.core
  (:use clojopts.core)
  (:use clojure.test)
  (:require clojopts.getopt))

(alter-var-root #'clojopts.getopt/*testing* (constantly true))

(deftest integration
  (is (= (clojopts "clojopts"
                   ["--name=src/clojure/awesome.clj" "-n" "10" "--" "1234"]
                   (optional-arg file f name "The file to use"
                     :type :file)
                   (with-arg lines n "How many lines to read"
                     :type :int))
         {:file (java.io.File. "src/clojure/awesome.clj")
          :lines 10
          :clojopts/more ["1234"]})))

(deftest corners
  (is (= (clojopts "_" ["-1" "etc"]
                   (with-arg 1 "Blah" :type :str))
         {:1 "etc"}))
  (is (= (clojopts "_" ["--nil=etc"]
                   (with-arg nil "Blah" :type :str))
         {:nil "etc"})))
