(ns cake-github.core
  (:use [clojure.java.shell :only [sh]]
        [clj-github.core :only [with-auth]]))

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

(defmulti formatter :seq-type)

(defmethod formatter :repo [{m :seq}]
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

(defmethod formatter :user [{m :seq}]
  (str
   (:name m) " - " (:email m)
   line
   "http://github.com/" (:login m) "\n"
   (format-keys
    (line-template (max-width m))
    (select-keys m [:location :login :company :contributions
                    :public_repo_count :public_gist_count :blog
                    :created_at :following_count :gravatar_id]))))

(defmethod formatter :gist [{m :seq}]
  (str
   "http://gist.github.com/" (:repo m) " - " (:description m)
   line
   (format-keys
    (line-template (max-width m))
    (select-keys m [:created_at :public :files :owner]))))

(defmethod formatter :generic [{m :seq}]
  (format-keys (line-template (max-width m)) m))

(defmethod formatter :sequence [{s :seq}]
  (apply str (apply concat (interpose ["\n"] (partition-all 10 (interpose ", " s))))))

(defmethod formatter :issue [{m :seq}]
  (str
   (:title m) " - #" (:number m)
   line
   (and (:body m) (str (:body m) "\n\n"))
   (order-format
    (line-template (max-width (dissoc m :number :title)))
    m [[:user :labels :position :state :votes :comments]
       [:created_at :updated_at :closed_at :gravatar_id]])))

(defmethod formatter :comment [{m :seq}]
  (str (:user m) " - " (:id m)
       line
       (and (:body m) (str (:body m) "\n\n"))
       (let [m (dissoc m :body :user :id :gravatar_id)]
         (format-keys (line-template (max-width m)) m))))

(defn format-result-helper [result map-type]
  (str "\n"
       (cond
        (map? result) (formatter {:seq result :seq-type map-type})
        (string? result) (str result "\n")
        (nil? result) "wut"
        (not (seq result)) "Nothing interested happened.\n"
        :else (str (formatter {:seq result :seq-type :sequence}) "\n"))))

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

(defmacro format-api [& body]
  `(with-auth auth
     (format-result ~@body)))

(defn options [opts & options]
  (some opts options))