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

(defn format-keys [lt m keyseq] (apply str (for [[k v] (select-keys m keyseq)] (format-line lt k v))))

(defn order-format [lt m keyseq] (apply str (concat (map (partial format-keys lt m) keyseq))))

(defn format-repo-result [m]
  (let [width (apply max
                     (map #(count (name (first %)))
                          (select-keys m [:watchers :pushed_at :forks
                                          :open_issues :created_at :pushed_at
                                          :clone_url :ssh_clone_url])))
        lt (line-template width)]
    (str
     (or (:owner m) (:username m)) "/"(:name m) (if (:fork m) " \u0470 " " ") "- " (:description m)
     line
     (when (:url m) (str (:url m) "\n"))
     (order-format lt m [[:watchers :forks :open_issues]
                         [:pushed_at :created_at]
                         [:clone_url :ssh_clone_url]]))))

;; This will likely become a multimethod.
(defn format-result-helper [result map-type]
  (str "\n"
       (cond
        (and (map? result) (= map-type :repo)) (format-repo-result result)
        (and (map? result) (= map-type :user)) result ;; Placeholder
        (string? result) result
        (nil? result) "wut"
        (not (seq result)) "Nothing interested happened."
        :else (apply str (interpose ", " result)))
       "\n"))

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
     (format-result-helper x map-type))))

(defn options [opts & options]
  (some opts options))


