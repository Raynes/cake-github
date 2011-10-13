(ns cake.tasks.repos
  (:use cake cake.core
        cake-github.core
        clj-github.repos))

(ts
 repos
 "Tasks for doing stuff with repositories."

 (deftask show
   "Display information about repositories that a user has. Pass the username
   of the user you'd like to stalk. You can optionally pass --results to limit
   the number of results shown. The default limit is three. "
   (format-result (show-repos ((:repos.show *opts*) 0))
                  :max (:results *opts*) :generate-clone-urls true))

 (deftask search
   "Search for repos on github. Pass in the query string. You can optionally pass
   --results to limit results, default being three.
   Optionally supply --language and --start-page to narrow results."
   (format-result
    (apply search-repos ((:repos.search *opts*) 0)
           (format-options *opts* :repos.search :results))
    :max (:results *opts*) :generate-clone-urls true))

 (deftask create
   "Create a repo. Pass in the name of the repository. Optionally pass --description,
   --homepage, and --public=[true|false]"
   (format-result
    (apply create-repo ((:repos.create *opts*) 0)
           (format-options *opts* :repos.create))
    :generate-clone-urls true))

 (deftask delete
   "Delete a repo. This cannot be undone. Use wisely. Pass in the name of the repo."
   (format-result (delete-repo ((:repos.delete *opts*) 0))))

 (deftask fork
   "Fork a repo. Pass in the owner of the repo you want to fork and the name of the repo."
   {[one two] :repos.fork}
   (format-result (fork-repo one two) :generate-clone-urls true))

 (deftask set
   "Set information about a repo. Pass in the owner of the repo, the name of the repo,
   the key corresponding to the information you want to change, and the value you want
   to set it to. Possible keys are description, homepage, has_wiki, has_downloads,
   and has_issues."
   {[one two three four] :repos.set}
   (format-result (set-repo-info one two three four)))

 (deftask info
   "Get infomation about a repository. Pass in the owner of the repo and the name of the repo."
   {[one two] :repos.info}
   (format-result (show-repo-info one two)))

 (deftask watch
   "Watch a repo. Pass in the owner of the repo and the name of the repo."
   {[one two] :repos.watch}
   (format-result (watch-repo one two)))

 (deftask unwatch
   "Unwatch a repo. Pass in the owner of the repo and the name of the repo."
   {[one two] :repos.unwatch}
   (format-result (unwatch-repo one two)))

 (deftask tags
   "List the tags on a repo. Pass in the owner of the repo and it's name."
   {[one two] :repos.tag}
   (format-result (show-tags one two) :map-type :generic))

 (deftask pushable
   "List all the repos that you don't own that you can push to. Limit results with --results.
   the default limit is three."
   (format-result (show-pushable auth) :max (:results *opts*)))

 (deftask network
   "Show a repository's full network. Pass in the owner and name of the repository.
   Limit results with --results. The default limit is three."
   {[one two] :repos.network results :results}
   (format-result (show-network one two) :max results))

 (deftask languages
   "List the languages used in a project. Values are in bytes. Limit results with --results.
   The default limit is three."
   {[one two] :repos.languages results :results}
   (format-result (show-languages one two) :max results :map-type :generic))

 (deftask contributors
   "List everybody who has contributed to a repo, including non-github-users.
   Pass the owner and name of the repository. Limit results with --results. Default limit is three."
   {[one two] :repos.contributors results :results}
   (format-result (show-contributors one two) :map-type :user :max results))

 (deftask collaborators
   "List collaborators on a repo. Pass the owner and name of the repo."
   {[one two] :repos.collaborators}
   (format-result (show-collaborators one two)))

 (deftask add-collaborator
   "Add a collaborator to your Github repository. Pass in the username you want to add and the
   name of the repo."
   {[one two] :repos.add-collaborator}
   (format-result (add-collaborator one two)))

 (deftask rm-collaborator
   "Remove a collaborator from your Github repository. Pass in the username you want to remove
   and the name of the repo."
   {[one two] :repos.rm-collaborator}
   (format-result (remove-collaborator one two)))

 (deftask deploy
   "List the deploy keys for a repository. Pass in the name of the repo."
   (format-result (show-deploy-keys ((:repos.deploy *opts*) 0))))

 (deftask add-deploy
   "Add a deploy key to a repository. Pass in the name of the repository, title of the key
   and the key itself."
   {[one two three] :repos.add-deploy}
   (format-result (add-deploy-key one two three)))

 (deftask rm-deploy
   "Remove a deploy key from a repository. Pass in the name of the repo and the title of the key."
   {[one two] :repos.rm-deploy}
   (format-result (remove-deploy-key one two)))

 (deftask visibility
   "Set a repository's visibility. Pass in the name of the repo and either 'public' or 'private'."
   {[one two] :repos.visibility}
   (format-result (set-repo-visibility one two))))