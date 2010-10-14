(ns cake-github.issues
  (:use cake cake.core
        cake-github.core
        clj-github.issues))

(deftask issues.open
  "Open an issue. Pass in the owner of the repo, name of the repo,
   title of the issue, and body of the issue."
  {[one two three four] :issues.open}
  (format-result (open-issue auth one two three four) :map-type :issue))

(deftask issues.info
  "Show data about an issue. Pass in the owner of the repo, name of the repo,
   and number of the issue."
  {[one two three] :issues.info}
  (format-result (show-issue auth one two three) :map-type :issue))

(deftask issues.close
  "Close an issue. Pass in the owner of the repo, name of the repo, and issue number."
  {[one two three] :issues.close}
  (format-result (close-issue auth one two three) :map-type :issue))

(deftask issues.comment
  "Comment on an issue. Pass in the owner of the repo, name of the repo, issue number,
   and the comment you want to add."
  {[one two three four] :issues.comment}
  (format-result (comment-issue auth one two three four) :map-type :comment))

(deftask issues.tag
  "Add a label to an issue or the system. Pass in the owner of the repo, name of the repo,
   the label you want to add, and the number of the issue or no number if you're adding
   the label to the system rather than to an issue."
  {[one two three four] :issues.tag}
  (format-result (add-label auth one two three four)))

(deftask issues.untag
  "Remove a label from an issue or the system. Pass in the owner of the repo, name of the repo,
   the label you want to remove, and the number of the issue or no number if you're removing
   the label from the system rather than from an issue."
  {[one two three four] :issues.untag}
  (format-result (remove-label auth one two three four)))

(deftask issues.tags
  "Show the tags on a repo. Pass in the owner and name of the repo."
  {[one two] :issues.tags}
  (format-result (show-labels auth one two)))

(deftask issues.comments
  "List the comments on an issue. Pass in the owner of the repo, name of the repo,
   and the number of the issue. You can limit results with --results. The default limit
   is three."
  {[one two three] :issues.comments results :results}
  (format-result (issue-comments auth one two three) :map-type :comment :max results))

(deftask issues.show
  "Show info about a repo's issues. Pass in the owner and name of the repo, and either
   'open' or 'closed'. Limit results with --results. The default limit is three."
  {[one two three] :issues.show results :results}
  (format-result (show-issues auth one two three) :map-type :issue :max results))

(deftask issues.reopen
  "Reopen a previously closed issue. Pass in the owner and name of the repo, and the issue
   number."
  {[one two three] :issues.reopen}
  (format-result (reopen-issue auth one two three) :map-type :issue))

(deftask issues.search
  "Search through issues. Pass in the owner and name of the repo, the state of the issues
   you want to search (either 'open' or 'closed') and the search term. Limit results with
   --results. Default limit is three."
  {[one two three four] :issues.search results :results}
  (format-result (search-issues auth one two three four) :map-type :issue :max results))