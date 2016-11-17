(ns suchcab.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [suchcab.events]
              [suchcab.subs]
              [suchcab.views :as views]
              [suchcab.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
