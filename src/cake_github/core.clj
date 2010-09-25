(ns cake-github.core
  (:use [clojure.java.shell :only [sh]]))

(def line "\n-------------------------------------------\n")

(defn get-config [parameter]
  (apply str (butlast (:out (sh "git" "config" "--global" (str "github." parameter))))))

(def auth {:username (get-config "user")
           :token (get-config "token")})

(defn clone-urls [res-map]
  (if (map? res-map)
    (let [owner (if-let [owner (:owner res-map)] owner (:username res-map))
          name (:name res-map)]
      (assoc res-map
        :clone_url (str "git://github.com/" owner "/" name ".git")
        :ssh_clone_url (str "git@github.com:" owner "/" name ".git")))
    res-map))

(defn format-options [m & keys]
  (apply concat (map (fn [[k v]] [k (first v)]) (apply dissoc m keys))))

(defn line-template [width] (str "%-" (inc width) "s %s"))

(defn format-line [lt k v] (str (format lt (.replaceAll (str (name k) ":") "_" " ") v) "\n"))

(defn format-keys [lt m] (apply str (for [[k v] m] (format-line lt k v))))

(defn order-format [lt m keyseq]
  (apply str (concat (map #(format-keys lt (select-keys m %)) keyseq))))

(defn max-width [m]
  (apply max (map #(count (name (first %))) m)))

(defn format-repo-map [m]
  (str
   (or (:owner m) (:username m)) "/"(:name m) (if (:fork m) " \u0470 " " ") "- " (:description m)
   line
   (when (:url m) (str (:url m) "\n"))
   (order-format
    (line-template
     (max-width
      (select-keys m [:watchers :pushed_at :forks
                      :open_issues :created_at :pushed_at
                      :clone_url :ssh_clone_url :homepage])))
    m [[:watchers :forks :open_issues :homepage]
       [:pushed_at :created_at]
       [:clone_url :ssh_clone_url]])))

(defn format-user-map [m]
  (str
   (:name m) " - " (:email m)
   line
   (format-keys
    (line-template (max-width m))
    (select-keys m [:location :login :company :contributions
                    :public_repo_count :public_gist_count :blog
                    :created_at :following_count]))))

(defn format-gist-map [m]
  (str
   (str "http://gist.github.com/" (:repo m)) " - " (:description m)
   line
   (format-keys
    (line-template (max-width m))
    (select-keys m [:created_at :public :files :owner]))))

(defn format-generic-map [m]
  (format-keys (line-template (max-width m)) m))

(defn format-sequence [s]
  (apply str (apply concat (interpose ["\n"] (partition-all 10 (interpose ", " s))))))


(defn format-result-helper [result map-type]
  (str "\n"
       (cond
        (map? result)
        (case
         map-type
         :repo (format-repo-map result)
         :user (format-user-map result)
         :generic (format-generic-map result)
         :gist (format-gist-map result))
        (string? result) (str result "\n")
        (nil? result) "wut"
        (not (seq result)) "Nothing interested happened.\n"
        :else (str (format-sequence result) "\n"))))

(defn option-to-int [opt default]
  (cond
   (vector? opt) (Integer/parseInt (first opt))
   (string? opt) (Integer/parseInt opt)
   (nil? opt) default
   :else opt))

(defn format-result [x & {:keys [max generate-clone-urls map-type]
                          :or {max 3 generate-clone-urls false map-type :repo}}]
  (println
   (if (some map? x)
     (apply str
            (map #(format-result-helper % map-type)
                 (let [maps (take (option-to-int max 3) x)]
                   (if generate-clone-urls
                     (map clone-urls maps)
                     maps))))
     (format-result-helper (if generate-clone-urls (clone-urls x) x) map-type))))

(defn options [opts & options]
  (some opts options))


