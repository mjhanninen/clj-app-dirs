(ns app-dirs.impl.linux
  (:require [app-dirs.impl.util :refer [apply-some]]
            [clojure.java.io :as io]
            [clojure.string :as str])
  (:import (java.io File)))

(defn- app-dir-suffix
  [^String _org ^String app]
  (some-> app
    str/trim
    not-empty
    str/lower-case
    (str/split #" +")
    (->> (str/join "_"))
    io/as-file))

(defn- abs-env-dir
  [^String envar]
  (when-let [f (some-> envar System/getenv io/as-file)]
    (when (.isAbsolute f)
      f)))

(defn home-dir
  []
  (some-> (or (System/getProperty "user.home")
              (System/getenv "HOME"))
    io/as-file))

(defn cache-dir
  ([]
   (or (abs-env-dir "XDG_CACHE_HOME")
       (some-> (home-dir) (io/file ".cache"))))
  ([org app]
   (apply-some io/file (cache-dir) (app-dir-suffix org app))))

(defn config-dir
  ([]
   (or (abs-env-dir "XDG_CONFIG_HOME")
       (some-> (home-dir) (io/file ".config"))))
  ([org app]
   (apply-some io/file (config-dir) (app-dir-suffix org app))))

(defn data-dir
  ([]
   (or (abs-env-dir "XDG_DATA_HOME")
       (some-> (home-dir) (io/file  ".local/share"))))
  ([org app]
   (apply-some io/file (data-dir) (app-dir-suffix org app))))

(defn data-local-dir
  ([]
   (data-dir))
  ([org app]
   (data-dir org app)))

(defn runtime-dir
  ([]
   (abs-env-dir "XDG_RUNTIME_DIR"))
  ([org app]
   (or (apply-some io/file
                   (abs-env-dir "XDG_RUNTIME_DIR")
                   (app-dir-suffix org app))
       ;; XXX(soija) Not too happy about this; the application might make
       ;;            false assumptions about the permissions along the dir
       ;;            path.
       (apply-some io/file
                   (System/getProperty "java.io.tmpdir")
                   (app-dir-suffix org app)
                   (System/getProperty "user.name")))))

(defn executable-dir
  []
  (or (abs-env-dir "XDG_BIN_HOME")
      (some-> (home-dir) (io/file ".local/bin"))))

(defn xdg-user-dirs-file
  []
  (when-let [f (some-> (config-dir) (io/file "user-dirs.dirs"))]
    (when (.isFile f)
      f)))

(defn extract-xdg-dir
  [^File home-dir ^String line]
  (when-let [[_ id path-str] (re-matches
                              #"^\s*XDG_([_A-Z0-9]+)_DIR *= *\"(.*)\" *$"
                              line)]
    (cond
      (str/starts-with? path-str "$HOME/") [id
                                            (io/file home-dir
                                                     (.substring path-str 6))]
      (str/starts-with? path-str "/") [id  (io/as-file path-str)])))

(def xdg-user-dirs-table (atom nil))

(defn xdg-user-dirs
  []
  (if-let [v @xdg-user-dirs-table]
    v
    (let [h (home-dir)]
      (some->> (xdg-user-dirs-file)
        slurp
        str/split-lines
        (keep #(extract-xdg-dir h %))
        (into {})
        (reset! xdg-user-dirs-table)))))

(defn xdg-user-dir
  [id]
  (some-> (xdg-user-dirs) (get id)))

(defn audio-dir
  []
  (xdg-user-dir "MUSIC"))

(defn desktop-dir
  []
  (xdg-user-dir "DESKTOP"))

(defn document-dir
  []
  (xdg-user-dir "DOCUMENTS"))

(defn download-dir
  []
  (xdg-user-dir "DOWNLOAD"))

(defn font-dir
  []
  (some-> (data-dir) (io/file "fonts")))

(defn picture-dir
  []
  (xdg-user-dir "PICTURES"))

(defn public-dir
  []
  (xdg-user-dir "PUBLICSHARE"))

(defn template-dir
  []
  (xdg-user-dir "TEMPLATES"))

(defn video-dir
  []
  (xdg-user-dir "VIDEOS"))
