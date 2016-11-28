(ns suchcab.components
    (:require 
      [clojure.string :as s]
      [reagent.core :as r]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; REUSABLE COMPONENTS!

(defn add-attr [hic k v]
  (assoc-in hic [1 k] v))

(defn add-elem [parent child]
  (conj parent child))

(defn toggle-class [el cl]
  ; only works with {:class "class1 class2"} not with [:div.classname ]
  (let [classes (set (s/split (:class (el 1)) " "))
        new-cl (if (contains? classes cl)
                 (disj classes cl)
                 (conj classes cl))]
    (assoc-in el [1 :class] (s/join " " new-cl))))

(defn link [txt url]
  [:a {:href url} txt])

(defn in
  ([atm input-type]
    [:input
     {:type input-type
      :on-change #(reset! atm (-> % .-target .-value))}])
  ([atm input-type id]
    (add-attr (in atm input-type) :id id))
  ([atm input-type id placeholder]
    (add-attr (in atm input-type id) :placeholder placeholder)))

(defn option [[k v]]
  [:option {:value (name k)} v])

(defn select
  [atm id optv] ; optv is an options-vector 
  (let [sel (add-attr [:select] :id id)
        opts (map option (apply array-map optv))]
       (apply conj sel (vec opts))))

(defn submit [id label on-click]
  [:button {:id id
            :on-click on-click} label "!"])

(defn range-input [atm id low high]
 (let [input (in atm "range" id)
       input-map (get input 1)]
    (assoc input 1 (assoc input-map :min low :max high :value @atm :step 1))))

;labeled form-element
(defn lblcmp [label cmp]
  [:div.form-group
    [:div.col-lg-2
       [:label.control-label label]]
    [:div.col-lg-10 cmp]])

(defn lbl-range [atm id low high]
    (lblcmp @atm (range-input atm id low high)))

(defn time-field [atm id date-obj] 
  (let [d  date-obj 
        hour   (. d getHours)
        am-pm  (r/atom  (if (and (>= hour 12) (not= hour 24)) "pm" "am"))
        hour   (r/atom (if (zero? (mod hour 12)) 12 hour))
        minute (. d getMinutes)
        minute (r/atom (if (< minute 10) (str "0" minute) minute))
        the-time (r/atom (str @hour ":" @minute))]
    [:div {:id id} 
    (toggle-class (in hour "text" (str id "-hour") @hour) "time")
    [:strong ":"]
    (toggle-class (in minute "text" (str id "-minute") @minute) "time")
    (toggle-class (select am-pm (str id "-am-pm") [:am "AM" :pm "PM"])
                  "time-select")]))
