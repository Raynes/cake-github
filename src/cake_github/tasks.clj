(ns cake-github.tasks
  (:use cake cake.core
        cake-github.core
        [clj-github repos]))

;; Repos ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(deftask github.repos.show
  "Display information about repositories that a user has. Pass the username
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
  "Delete a repo. This cannot be undone. Use wisely. Pass in the name of the repo."
  (format-result (delete-repo auth ((:github.repos.delete *opts*) 0))))

(deftask github.repos.fork
  "Fork a repo. Pass in the owner of the repo you want to fork and the name of the repo."
  (format-result
   (fork-repo auth ((:github.repos.fork *opts*) 0) ((:github.repos.fork *opts*) 1))
   :generate-clone-urls true))

(deftask github.repos.set
  "Set information about a repo. Pass in the owner of the repo, the name of the repo,
   the key corresponding to the information you want to change, and the value you want
   to set it to. Possible keys are description, homepage, has_wiki, has_downloads,
   and has_issues."
  (let [opts (:github.repos.set *opts*)]
    (format-result (set-repo-info auth (opts 0) (opts 1) (opts 2) (opts 3)))))

(deftask github.repos.info
  "Get infomation about a repository. Pass in the owner of the repo and the name of the repo."
  (format-result
   (show-repo-info auth ((:github.repos.info *opts*) 0) ((:github.repos.info *opts*) 1))))

(deftask github.repos.watch
  "Watch a repo. Pass in the owner of the repo and the name of the repo."
  (format-result
   (watch-repo auth ((:github.repos.watch *opts*) 0) ((:github.repos.watch *opts*) 1))))

(deftask github.repos.unwatch
  "Unwatch a repo. Pass in the owner of the repo and the name of the repo."
  (format-result
   (unwatch-repo auth ((:github.repos.unwatch *opts*) 0) ((:github.repos.unwatch *opts*) 1))))

(deftask github.repos.tags
  "List the tags on a repo. Pass in the owner of the repo and it's name."
  (format-result
   (show-tags auth ((:github.repos.tags *opts*) 0) ((:github.repos.tags *opts*) 1))
   :map-type :generic))