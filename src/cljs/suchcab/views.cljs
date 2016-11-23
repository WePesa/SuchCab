(ns suchcab.views
    (:require 
      [suchcab.components :as c]
      [reagent.core :as r]
      [re-frame.core :as re-frame]))

;;;;;;;;;;;;;;;;;;;;;;
;; S U C H <> C A B ;;
;;;;;;;;;;;;;;;;;;;;;;
; PANELS TO BUILD

; Login page

;TODO: Form validation!
(defn login-handler [usr pass] (js/console.log "submitting username: " @usr "\npassword: " @pass "\n"))




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; PANELS!

(defn login-panel []
  (let [username (r/atom "")
        password (r/atom "")]
    (fn []
      [:div#login-panel 
        (c/in username "text" "login-user-input" "username")
        (c/in password "password" "login-pass-input" "password")
        (c/submit "login-user-submit" "Log In"  #(login-handler username password))])))

; Signup page
;   Name / phone number / email / payment (coinbase probably?)
;TODO: Form validation!
(defn signup-handler [usr pass phone email] 
  (js/console.log 
    "submitting username: " @usr
    "\npassword: " @pass
    "\nphone no: " @phone
    "\nemail:::: " @email))


(defn signup-panel []
  (let [username (r/atom "")
        password (r/atom "")
        phone    (r/atom "")
        email    (r/atom "")]
    (fn [] 
      [:div#signup-panel
       (c/in username "text" "signup-user-input" "username")
       (c/in password "password" "signup-user-input" "password")
       (c/in phone "tel" "signup-phone-input" "phone-number")
       (c/in email "email" "signup-email-input" "email address")
       (c/submit "signup-submit" "Submit User Info" #(signup-handler username password phone email))])))

; Driver Signup
;   Own name 
;   Company of Employment 
;     dropdown of companies
; FOR LATER:
;   references 
;   picture
(defn driver-signup-handler [real-name company] 
  (js/console.log 
    "real-name: "
    @real-name
    "\ncompany  : "
    @company))

(defn driver-signup-panel []
  (let [real-name (r/atom "")
        company (r/atom "")]
    (fn []
      [:div#driver-signup-panel
       (c/in real-name "text" "driver-signup-name" "Real Name")
       (c/in company "text" "driver-signup-company" "Company")
       (c/submit "driver-signup-submit" "Submit Driver Info" #(driver-signup-handler real-name company))])))

; Logged in: Customer idle

;  ride-request panel
;    [price start (optional: description end)]
;      submit-button

(defn ride-request-panel []
  (let [price        (r/atom 20)
        start-time   (r/atom 0)
        end-time     (r/atom 0)
        origin-point (r/atom false)
        end-point    (r/atom false)
        description  (r/atom "pedicab ride")]
    (fn []
      [:div 
         [:div 
          [:h4 "price"] 
          (c/lbl-range price "request-price-input" 15 100)]
         [:div 
          [:h4 "start time"] 
          (c/time-field start-time "request-start-time" (js/Date.))]
         [:div 
          [:h4 "end time"] 
          (c/time-field end-time "request-start-time" (js/Date.))]])))

;  active offers
;  special offers
;    description
;     available hours
;     rate

;    offer
;     terms (one of the following)
;      - x dollars for y minutes
;      - x dollars flat rate
;      
;     driver
;       Company
;       Full Name
;       Picture
;     consider... 
;       call or chat
;       counter-offer
;       confirm

; confirm: waiting driver confirmation
;    small panel
;    3 mins to cancel

; denied

; confirmed
;   waiting for driver (est X mins)
;   (start ride button) both users "commence-ride" to start ride

; ride-begun screen
;   current-charge
;   small-timer
;   current-rate
;   end ride button

; Logged in: Driver

; * Go active
;   >  Rate: 
;      either [amt] per [timeperiod]
;      or     [amt] for [description]
;   >  Timeframe
;      start/finish

; * active bids

; * recent transactions
;   > Transaction
;       Anon or Public parties
;       final price

; Settings
;   > Payment options
;   > Company
;     name/phonenumber
;     picture

(defn demo-panel [label component]
  [:div.scbox [:h4 label] [component] ])

(defn main-panel []
  ; first, check if theres a cookie.
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div 
       [:h2 "List of components so far" ]
       (demo-panel "login-panel" login-panel)
       (demo-panel "signup-panel" signup-panel)
       (demo-panel "driver-signup-panel" driver-signup-panel)
       (demo-panel "ride-request-panel" ride-request-panel)])))
