(ns cake-github.issues
  (:use cake cake.core
        cake-github.core
        clj-github.issues))

(deftask github.issues.open
  "Open an issue. Pass in the owner of the repo, name of the repo,
   title of the issue, and body of the issue."
  [{[one two three four] :github.issues.open}]
  (format-result (open-issue auth one two three four) :map-type :issue))

(deftask github.issues.info
  "Show data about an issue. Pass in the owner of the repo, name of the repo,
   and number of the issue."
  [{[one two three] :github.issues.info}]
  (format-result (show-issue auth one two three) :map-type :issue))

(deftask github.issues.close
  "Close an issue. Pass in the owner of the repo, name of the repo, and issue number."
  [{[one two three] :github.issues.close}]
  (format-result (close-issue auth one two three) :map-type :issue))

(deftask github.issues.comment
  "Comment on an issue. Pass in the owner of the repo, name of the repo, issue number,
   and the comment you want to add."
  [{[one two three four] :github.issues.comment}]
  (format-result (comment-issue auth one two three four) :map-type :comment))