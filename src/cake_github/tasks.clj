(ns cake-github.tasks
  (:use cake cake.core
        cake-github.core
        [clj-github repos users gists]))

;; Repos ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(deftask github.repos.show
  "Display information about repositories that a user has. Pass the username
   of the user you'd like to stalk. You can optionally pass --results to limit
   the number of results shown. The default limit is three. "
  (format-result (show-repos auth ((:github.repos.show *opts*) 0))
                 :max (:results *opts*) :generate-clone-urls true))

(deftask github.repos.search
  "Search for repos on github. Pass in the query string. You can optionally pass
   --results to limit results, default being three.
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

(deftask github.repos.pushable
  "List all the repos that you don't own that you can push to. Limit results with --results.
   the default limit is three."
  (format-result (show-pushable auth) :max (:results *opts*)))

(deftask github.repos.network
  "Show a repository's full network. Pass in the owner and name of the repository.
   Limit results with --results. The default limit is three."
  (format-result
   (show-network auth ((:github.repos.network *opts*) 0) ((:github.repos.network *opts*) 1))
   :max (:results *opts*)))

(deftask github.repos.languages
  "List the languages used in a project. Values are in bytes. Limit results with --results.
   The default limit is three."
  (format-result
   (show-languages auth ((:github.repos.languages *opts*) 0) ((:github.repos.languages *opts*) 1))
   :max (:results *opts*) :map-type :generic))

(deftask github.repos.contributors
  "List everybody who has contributed to a repo, including non-github-users.
   Pass the owner and name of the repository. Limit results with --results. Default limit is three."
  (format-result
   (show-contributors auth
                      ((:github.repos.contributors *opts*) 0)
                      ((:github.repos.contributors *opts*) 1))
   :map-type :user :max (:results *opts*)))

(deftask github.repos.collaborators
  "List collaborators on a repo. Pass the owner and name of the repo."
  (format-result
   (show-collaborators auth
                       ((:github.repos.collaborators *opts*) 0)
                       ((:github.repos.collaborators *opts*) 1))))

(deftask github.repos.add-collaborator
  "Add a collaborator to your Github repository. Pass in the username you want to add and the
   name of the repo."
  (format-result
   (add-collaborator auth
                     ((:github.repos.add-collaborator *opts*) 0)
                     ((:github.repos.add-collaborator *opts*) 1))))

(deftask github.repos.rm-collaborator
  "Remove a collaborator from your Github repository. Pass in the username you want to remove
   and the name of the repo."
  (format-result
   (remove-collaborator auth
                        ((:github.repos.rm-collaborator *opts*) 0)
                        ((:github.repos.rm-collaborator *opts*) 1))))

(deftask github.repos.deploy
  "List the deploy keys for a repository. Pass in the name of the repo."
  (format-result
   (show-deploy-keys auth ((:github.repos.deploy *opts*) 0))))

(deftask github.repos.add-deploy
  "Add a deploy key to a repository. Pass in the name of the repository, title of the key
   and the key itself."
  (format-result
   (add-deploy-key auth
                   ((:github.repos.add-deploy *opts*) 0)
                   ((:github.repos.add-deploy *opts*) 1)
                   ((:github.repos.add-deploy *opts*) 2))))

(deftask github.repos.rm-deploy
  "Remove a deploy key from a repository. Pass in the name of the repo and the title of the key."
  (format-result
   (remove-deploy-key auth
                      ((:github.repos.rm-deploy *opts*) 0)
                      ((:github.repos.rm-deploy *opts*) 1))))

(deftask github.repos.visibility
  "Set a repository's visibility. Pass in the name of the repo and either 'public' or 'private'."
  (format-result
   (set-repo-visibility ((:github.repos.visibility *opts*) 0) ((:github.repos.visibility *opts*) 1))))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; Users ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(deftask github.users.follow
  "Follow a user. Pass in the name of the user you want to follow."
  (format-result (follow auth ((:github.users.follow *opts*) 0))))

(deftask github.users.unfollow
  "Unfollow a user. Pass in the name of the user you want to unfollow."
  (format-result (unfollow auth ((:github.users.unfollow *opts*) 0))))

(deftask github.users.search
  "Search users. Pass in the query string. Optionally pass --results to limit results.
   Default is three."
  (format-result
   (search-users auth ((:github.users.search *opts*) 0)) :map-type :user :max (:results *opts*)))

(deftask github.users.followers
  "Find out what users are following a user. Pass in the user."
  (format-result (show-followers auth ((:github.users.followers *opts*) 0))))

(deftask github.users.following
  "Find out what users another user is following. Pass in the user."
  (format-result (show-following auth ((:github.users.following *opts*) 0))))

(deftask github.users.info
  "View detailed information about a user. Pass in the name of the user."
  (format-result (show-user-info auth ((:github.users.info *opts*) 0)) :map-type :user))

(deftask github.users.watching
  "Find out what repos a user is watching. Pass in the name of the user. Optionally
   pass --results to limit results. Default is three."
  (format-result
   (show-watching auth ((:github.users.watching *opts*) 0))
   :map-type :repo :max (:results *opts*)))

(deftask github.users.set
  "Set information about a user. Pass in the username, a key, and a value to set that key
   to. Possible keys are name, email, blog, company, and location."
  (format-result
   (user-set auth
             ((:github.users.set *opts*) 0)
             ((:github.users.set *opts*) 1)
             ((:github.users.set *opts*) 2))
   :map-type :user))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Gists ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(deftask github.gists.new
  "Gist a file. Pass in the path to the file relative to where this task is called."
  (format-result
   (str
    "http://gist.github.com/"
    (:repo
     (let [gist-file ((:github.gists.new *opts*) 0)]
       (new-gist auth
                 (last (.split gist-file "/"))
                 (slurp (str (System/getProperty "user.dir") "/" gist-file))))))))

(deftask github.gists.contents
  "Show the contents of a gist. Pass in the id of the gist and the file name."
  (format-result
   (show-gist auth ((:github.gists.contents *opts*) 0) ((:github.gists.contents *opts*) 1))))

(deftask github.gists.show
  "Show metadata for another user's gists. Pass in the name of the user. Pass in --results
   to limit results. The default limit is three."
  (format-result
   (show-users-gists auth ((:github.gists.show *opts*) 0))
   :map-type :gist :max (:results *opts*)))