(defproject cake-github "0.1.0"
  :description "A cake plugin providing a command-line interface to the Github API."
  :dependencies [[clojure "1.2.0"]
                 [clj-github "1.0.0-SNAPSHOT"]]
  :dev-dependencies [[swank-clojure "1.2.1"]
                     [clj-github "1.0.0-SNAPSHOT"]]
  :tasks [cake-github.tasks])
