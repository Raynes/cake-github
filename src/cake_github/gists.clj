(ns cake-github.gists
  (:use cake cake.core
        cake-github.core
        clj-github.gists))

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
  [{[one two] :github.gists.contents}]
  (format-result (show-gist auth one two)))

(deftask github.gists.show
  "Show metadata for another user's gists. Pass in the name of the user. Pass in --results
   to limit results. The default limit is three."
  (format-result
   (show-users-gists auth ((:github.gists.show *opts*) 0))
   :map-type :gist :max (:results *opts*)))

(deftask github.gists.meta
  "Show a gist's metadata. Pass in the gist's ID."
  (format-result (show-gist-meta auth ((:github.gists.meta *opts*) 0)) :map-type :gist))