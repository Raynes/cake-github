(ns cake-github.users
  (:use cake cake.core
        cake-github.core
        clj-github.users))

(deftask users.follow
  "Follow a user. Pass in the name of the user you want to follow."
  (format-result (follow auth ((:users.follow *opts*) 0))))

(deftask users.unfollow
  "Unfollow a user. Pass in the name of the user you want to unfollow."
  (format-result (unfollow auth ((:users.unfollow *opts*) 0))))

(deftask users.search
  "Search users. Pass in the query string. Optionally pass --results to limit results.
   Default is three."
  (format-result
   (search-users auth ((:users.search *opts*) 0)) :map-type :user :max (:results *opts*)))

(deftask users.followers
  "Find out what users are following a user. Pass in the user."
  (format-result (show-followers auth ((:users.followers *opts*) 0))))

(deftask users.following
  "Find out what users another user is following. Pass in the user."
  (format-result (show-following auth ((:users.following *opts*) 0))))

(deftask users.info
  "View detailed information about a user. Pass in the name of the user."
  (format-result (show-user-info auth ((:users.info *opts*) 0)) :map-type :user))

(deftask users.watching
  "Find out what repos a user is watching. Pass in the name of the user. Optionally
   pass --results to limit results. Default is three."
  (format-result
   (show-watching auth ((:users.watching *opts*) 0))
   :map-type :repo :max (:results *opts*)))

(deftask users.set
  "Set information about a user. Pass in the username, a key, and a value to set that key
   to. Possible keys are name, email, blog, company, and location."
  {[one two three] :users.set}
  (format-result (user-set auth one two three) :map-type :user))