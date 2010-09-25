(ns cake-github.users
  (:use cake cake.core
        cake-github.core
        clj-github.users))

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
  [{[one two three] :github.users.set}]
  (format-result (user-set auth one two three) :map-type :user))