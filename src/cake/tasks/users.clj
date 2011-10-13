(ns cake.tasks.users
  (:use cake cake.core
        cake-github.core
        clj-github.users))

(ts
 users
 "Tasks for doing stuff with users."
 (deftask follow
   "Follow a user. Pass in the name of the user you want to follow."
   (format-result (follow ((:users.follow *opts*) 0))))

 (deftask unfollow
   "Unfollow a user. Pass in the name of the user you want to unfollow."
   (format-result (unfollow ((:users.unfollow *opts*) 0))))

 (deftask search
   "Search users. Pass in the query string. Optionally pass --results to limit results.
   Default is three."
   (format-result
    (search-users ((:users.search *opts*) 0)) :map-type :user :max (:results *opts*)))

 (deftask followers
   "Find out what users are following a user. Pass in the user."
   (format-result (show-followers ((:users.followers *opts*) 0))))

 (deftask following
   "Find out what users another user is following. Pass in the user."
   (format-result (show-following ((:users.following *opts*) 0))))

 (deftask info
   "View detailed information about a user. Pass in the name of the user."
   (format-result (show-user-info ((:users.info *opts*) 0)) :map-type :user))

 (deftask watching
   "Find out what repos a user is watching. Pass in the name of the user. Optionally
   pass --results to limit results. Default is three."
   (format-result
    (show-watching ((:users.watching *opts*) 0))
    :map-type :repo :max (:results *opts*)))

 (deftask set
   "Set information about a user. Pass in the username, a key, and a value to set that key
   to. Possible keys are name, email, blog, company, and location."
   {[one two three] :users.set}
   (format-result (set-user one two three) :map-type :user)))