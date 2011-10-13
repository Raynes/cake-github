(ns cake.tasks.github
  (:use [cake.core :only [require-tasks]]))

(require-tasks [cake.tasks issues users repos gists])