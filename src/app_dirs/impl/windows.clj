(ns app-dirs.impl.windows
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:import (java.lang UnsupportedOperationException)))

(defn- not-implemented
  [id]
  (throw (UnsupportedOperationException.
          (str id " not implemented yet"))))

(defn- sanitize
  [^String s]
  (some-> s str/trim not-empty))

(defn app-rel-dir
  [^String org ^String app]
  (let [o (sanitize org)
        a (sanitize app)]
    (some->> (cond-> []
           o (conj o)
           a (conj a))
      not-empty
      (apply io/file))))

(defn home-dir
  []
  (some-> (or (System/getProperty "user.home")
              (System/getenv "HOME"))
    io/as-file))

(defn cache-dir
  []
  (not-implemented "cache-dir"))

(defn config-dir
  []
  (not-implemented "config-dir"))

(defn data-dir
  []
  (not-implemented "data-dir"))

(defn data-local-dir
  []
  (not-implemented "data-local-dir"))

(defn runtime-dir
  []
  (not-implemented "runtime-dir"))

(defn executable-dir
  []
  (not-implemented "executable-dir"))

(defn audio-dir
  []
  (not-implemented "audio-dir"))

(defn desktop-dir
  []
  (not-implemented "desktop-dir"))

(defn document-dir
  []
  (not-implemented "document-dir"))

(defn download-dir
  []
  (not-implemented "download-dir"))

(defn font-dir
  []
  (not-implemented "font-dir"))

(defn picture-dir
  []
  (not-implemented "picture-dir"))

(defn public-dir
  []
  (not-implemented "public-dir"))

(defn template-dir
  []
  (not-implemented "template-dir"))

(defn video-dir
  []
  (not-implemented "video-dir"))
