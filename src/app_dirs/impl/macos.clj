(ns app-dirs.impl.macos
  (:require [app-dirs.impl.util :refer [apply-some]]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn app-dir-suffix
  [^String _org ^String app]
  (some-> app str/trim not-empty io/as-file))

(defn home-dir
  []
  (some-> (or (System/getProperty "user.home")
              (System/getenv "HOME"))
    io/as-file))

(defn cache-dir
  ([]
   (some-> (home-dir) (io/file "Library/Caches")))
  ([org app]
   (apply-some io/file (cache-dir) (app-dir-suffix org app))))

(defn config-dir
  ([]
   (some-> (home-dir) (io/file "Library/Preferences")))
  ([org app]
   (apply-some io/file (config-dir) (app-dir-suffix org app))))

(defn data-dir
  ([]
   (some-> (home-dir) (io/file "Library/Application Support")))
  ([org app]
   (apply-some io/file (data-dir) (app-dir-suffix org app))))

(defn data-local-dir
  ([]
   (data-dir))
  ([org app]
   (data-dir org app)))

(defn runtime-dir
  ([]
   nil)
  ([org app]
   ;; See comment in `data-dir.impl.linux/runtime-dir`
   (apply-some io/file
               (System/getProperty "java.io.tmpdir")
               (app-dir-suffix org app)
               (System/getProperty "user.name"))))

(defn audio-dir
  []
  (some-> (home-dir) (io/file "Music")))

(defn desktop-dir
  []
  (some-> (home-dir) (io/file "Desktop")))

(defn document-dir
  []
  (some-> (home-dir) (io/file "Documents")))

(defn download-dir
  []
  (some-> (home-dir) (io/file "Downloads")))

(defn font-dir
  []
  (some-> (home-dir) (io/file "Library/Fonts")))

(defn picture-dir
  []
  (some-> (home-dir) (io/file "Pictures")))

(defn public-dir
  []
  (some-> (home-dir) (io/file "Public")))

(defn template-dir
  []
  nil)

(defn video-dir
  []
  (some-> (home-dir) (io/file "Movies")))
