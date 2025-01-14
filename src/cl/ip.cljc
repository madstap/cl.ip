(ns cl.ip
  (:refer-clojure :exclude [set get])
  #?(:bb (:require [babashka.process :as proc]
                   [clojure.string :as str])
     :clj (:import (java.awt.datatransfer StringSelection DataFlavor)
                   (java.awt Toolkit))))

;; TODO: Linux with something other than xclip
;; TODO: OSX
;; TODO: Windows
#?(:bb
   (def platform
     (delay
       (let [os-name (str/lower-case (System/getProperty "os.name"))]
         (cond (= "linux" os-name) :xclip
               (str/includes? os-name "win") :windows)))))

#?(:bb
   (def set-cmd
     (delay
       (case @platform
         :xclip ["xclip" "-selection" "c"]))))

#?(:bb
   (def get-cmd
     (delay
       (case @platform
         :xclip ["xclip" "-selection" "c" "-o"]))))

;; TODO: Node based things.

(defn set [s]
  #?(:bb
     ;; HACK: We use process instead of sh here to avoid xclip blocking.
     ;; TODO: Figure out why this is and maybe do something else?
     ;; Is this a leak of some kind?
     ;; https://stackoverflow.com/questions/77890133/emacs-shell-command-with-xclip-hangs-when-copying-to-clipboard
     ;; https://stackoverflow.com/questions/19254114/xclip-does-not-terminate-when-tracing-it/77154953#77154953
     (do (apply proc/process {:in s} @set-cmd)
         s)
     :clj
     (do (-> (.getSystemClipboard (Toolkit/getDefaultToolkit))
             (.setContents (StringSelection. (str s)) nil))
         s)))

(defn get []
  #?(:bb (:out (apply proc/sh @get-cmd))
     :clj
     (-> (java.awt.Toolkit/getDefaultToolkit)
         .getSystemClipboard
         (.getContents nil)
         (.getTransferData DataFlavor/stringFlavor))))

(comment

  (set "foobar")

  ;; TODO: Wow, java.awt stuff is really slow on my box, why is that?
  ;; Maybe it's better to just shell out on the jvm as well...
  (time (get))

  )
