(ns cake.tasks.gists
  (:use cake cake.core
        cake-github.core
        clj-github.gists))

(ts
 gists
 "Stuff for gisting."

 (deftask new
   "Gist a file. Pass in the path to the file relative to where this task is called."
   (format-result
    (str
     "http://gist.github.com/"
     (:repo
      (let [gist-file ((:gists.new *opts*) 0)]
        (new-gist auth
                  (last (.split gist-file "/"))
                  (slurp (str (System/getProperty "user.dir") "/" gist-file))))))))

 (deftask contents
   "Show the contents of a gist. Pass in the id of the gist and the file name."
   {[one two] :gists.contents}
   (format-result (show-gist one two)))

 (deftask show
   "Show metadata for another user's gists. Pass in the name of the user. Pass in --results
   to limit results. The default limit is three."
   (format-result
    (show-users-gists ((:gists.show *opts*) 0))
    :map-type :gist :max (:results *opts*)))

 (deftask meta
   "Show a gist's metadata. Pass in the gist's ID."
   (format-result (show-gist-meta ((:gists.meta *opts*) 0)) :map-type :gist)))