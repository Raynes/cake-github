(ns cake-github.repos
  (:use cake cake.core
        cake-github.core
        clj-github.repos))

(deftask repos.show
  "Display information about repositories that a user has. Pass the username
   of the user you'd like to stalk. You can optionally pass --results to limit
   the number of results shown. The default limit is three. "
  (format-result (show-repos auth ((:repos.show *opts*) 0))
                 :max (:results *opts*) :generate-clone-urls true))

(deftask repos.search
  "Search for repos on github. Pass in the query string. You can optionally pass
   --results to limit results, default being three.
   Optionally supply --language and --start-page to narrow results."
  (format-result
   (apply search-repos auth ((:repos.search *opts*) 0)
          (format-options *opts* :repos.search :results))
   :max (:results *opts*) :generate-clone-urls true))

(deftask repos.create
  "Create a repo. Pass in the name of the repository. Optionally pass --description,
   --homepage, and --public=[true|false]"
  (format-result
   (apply create-repo auth ((:repos.create *opts*) 0)
          (format-options *opts* :repos.create))
   :generate-clone-urls true))

(deftask repos.delete
  "Delete a repo. This cannot be undone. Use wisely. Pass in the name of the repo."
  (format-result (delete-repo auth ((:repos.delete *opts*) 0))))

(deftask repos.fork
  "Fork a repo. Pass in the owner of the repo you want to fork and the name of the repo."
  {[one two] :repos.fork}
  (format-result (fork-repo auth one two) :generate-clone-urls true))

(deftask repos.set
  "Set information about a repo. Pass in the owner of the repo, the name of the repo,
   the key corresponding to the information you want to change, and the value you want
   to set it to. Possible keys are description, homepage, has_wiki, has_downloads,
   and has_issues."
  {[one two three four] :repos.set}
  (format-result (set-repo-info auth one two three four)))

(deftask repos.info
  "Get infomation about a repository. Pass in the owner of the repo and the name of the repo."
  {[one two] :repos.info}
  (format-result (show-repo-info auth one two)))

(deftask repos.watch
  "Watch a repo. Pass in the owner of the repo and the name of the repo."
  {[one two] :repos.watch}
  (format-result (watch-repo auth one two)))

(deftask repos.unwatch
  "Unwatch a repo. Pass in the owner of the repo and the name of the repo."
  {[one two] :repos.unwatch}
  (format-result (unwatch-repo auth one two)))

(deftask repos.tags
  "List the tags on a repo. Pass in the owner of the repo and it's name."
  {[one two] :repos.tag}
  (format-result (show-tags auth one two) :map-type :generic))

(deftask repos.pushable
  "List all the repos that you don't own that you can push to. Limit results with --results.
   the default limit is three."
  (format-result (show-pushable auth) :max (:results *opts*)))

(deftask repos.network
  "Show a repository's full network. Pass in the owner and name of the repository.
   Limit results with --results. The default limit is three."
  {[one two] :repos.network results :results}
  (format-result (show-network auth one two) :max results))

(deftask repos.languages
  "List the languages used in a project. Values are in bytes. Limit results with --results.
   The default limit is three."
  {[one two] :repos.languages results :results}
  (format-result (show-languages auth one two) :max results :map-type :generic))

(deftask repos.contributors
  "List everybody who has contributed to a repo, including non-github-users.
   Pass the owner and name of the repository. Limit results with --results. Default limit is three."
  {[one two] :repos.contributors results :results}
  (format-result (show-contributors auth one two) :map-type :user :max results))

(deftask repos.collaborators
  "List collaborators on a repo. Pass the owner and name of the repo."
  {[one two] :repos.collaborators}
  (format-result (show-collaborators auth one two)))

(deftask repos.add-collaborator
  "Add a collaborator to your Github repository. Pass in the username you want to add and the
   name of the repo."
  {[one two] :repos.add-collaborator}
  (format-result (add-collaborator auth one two)))

(deftask repos.rm-collaborator
  "Remove a collaborator from your Github repository. Pass in the username you want to remove
   and the name of the repo."
  {[one two] :repos.rm-collaborator}
  (format-result (remove-collaborator auth one two)))

(deftask repos.deploy
  "List the deploy keys for a repository. Pass in the name of the repo."
  (format-result (show-deploy-keys auth ((:repos.deploy *opts*) 0))))

(deftask repos.add-deploy
  "Add a deploy key to a repository. Pass in the name of the repository, title of the key
   and the key itself."
  {[one two three] :repos.add-deploy}
  (format-result (add-deploy-key auth one two three)))

(deftask repos.rm-deploy
  "Remove a deploy key from a repository. Pass in the name of the repo and the title of the key."
  {[one two] :repos.rm-deploy}
  (format-result (remove-deploy-key auth one two)))

(deftask repos.visibility
  "Set a repository's visibility. Pass in the name of the repo and either 'public' or 'private'."
  {[one two] :repos.visibility}
  (format-result (set-repo-visibility one two)))