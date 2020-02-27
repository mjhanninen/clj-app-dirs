(ns app-dirs
  (:require [app-dirs.impl.linux :as l]
            [app-dirs.impl.macos :as m]
            [app-dirs.impl.util :refer [os]]
            [app-dirs.impl.windows :as w]))

(defn- dispatch
  [linux-fn macos-fn windows-fn & args]
  (apply (case (os)
           :linux linux-fn
           :macos macos-fn
           :windows windows-fn)
         args))

(defn cache-dir
  [org app]
  (dispatch l/cache-dir m/cache-dir w/cache-dir org app))

(defn config-dir
  [org app]
  (dispatch l/config-dir m/config-dir w/config-dir org app))

(defn data-dir
  [org app]
  (dispatch l/data-dir m/data-dir w/data-dir org app))

(defn data-local-dir
  [org app]
  (dispatch l/data-local-dir m/data-local-dir w/data-local-dir org app))

(defn runtime-dir
  [org app]
  (dispatch l/runtime-dir m/runtime-dir w/runtime-dir org app))

(defn home-dir
  []
  (dispatch l/home-dir m/home-dir w/home-dir))

(defn audio-dir
  []
  (dispatch l/audio-dir m/audio-dir w/audio-dir))

(defn desktop-dir
  []
  (dispatch l/desktop-dir m/desktop-dir w/desktop-dir))

(defn document-dir
  []
  (dispatch l/document-dir m/document-dir w/document-dir))

(defn download-dir
  []
  (dispatch l/download-dir m/download-dir w/download-dir))

(defn font-dir
  []
  (dispatch l/font-dir m/font-dir w/font-dir))

(defn picture-dir
  []
  (dispatch l/picture-dir m/picture-dir w/picture-dir))

(defn public-dir
  []
  (dispatch l/public-dir m/public-dir w/public-dir))

(defn template-dir
  []
  (dispatch l/template-dir m/template-dir w/template-dir))

(defn video-dir
  []
  (dispatch l/video-dir m/video-dir w/video-dir))
