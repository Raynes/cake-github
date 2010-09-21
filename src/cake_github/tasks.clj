(ns cake-github.tasks
  (:use cake cake.core
        cake-github.core
        [clj-github repos]))

(deftask github.repos.show
  "Displays information about repositories that a user has. Pass the username
   of the user you'd like to stalk. You can optionally pass --results=n to limit
   the number of results shown. The default limit is 3. Supply --results=all to not
   limit results."
  (format-result (show-repos auth ((:github.repos.show *opts*) 0))
                 :max (:results *opts*) :generate-clone-urls true))

(deftask github.repos.search
  "Search for repos on github. Pass in the query string. You can optionally pass
   --results=n to limit results (=max to not limit results), default being three.
   Optionally supply --language and --start-page to narrow results."
  (format-result
   (apply search-repos auth ((:github.repos.search *opts*) 0)
          (format-options *opts* :github.repos.search :results))
   :max (:results *opts*) :generate-clone-urls true))

(deftask github.repos.create
  "Create a repo. Pass in the name of the repository. Optionally pass --description,
   --homepage, and --public=[true|false]"
  (format-result
   (apply create-repo auth ((:github.repos.create *opts*) 0)
          (format-options *opts* :github.repos.create))
   :generate-clone-urls true))

(deftask github.repos.delete
  "Deletes a repo. This cannot be undone. Use wisely. Pass in the name of the repo."
  (format-result (delete-repo auth ((:github.repos.delete *opts*) 0))))
