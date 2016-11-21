(ns suchcab.components
    (:require 
      [reagent.core :as r]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; REUSABLE COMPONENTS!

(defn text
  ([atm input-type id]
    [:input 
     {:id id
      :type input-type
      :on-change #((reset! atm (-> % .-target .-value)))}])
  ([atm input-type id placeholder]
   (assoc-in (text atm input-type id) [1 :placeholder] placeholder)))

(defn submit [id label on-click]
  [:button {:id id
            :on-click on-click} label "!"])

;TODO FIX THESE DAMN RANGE COMPONENTS ?

(defn lblcmp [label cmp]
  [:div.form-group
   [:label.control-label.col-lg-2 label]
   [:div.col-lg-10 cmp]])

(defn range-input [atm id low high]
 (let [input (text atm "range" id)
       input-map (get input 1)]
   (fn []
    (assoc input 1 (assoc input-map :low low :high high :value @atm :step 1)))))

(defn label-range [atm id low high]
  ((lblcmp @atm (range-input atm id low high))))




