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
(defn login-handler [usr pass] 
  (js/console.log 
    "submitting username: " @usr "\npassword: " @pass "\n"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; PANELS!

(defn login-panel []
  (let [username (r/atom "")
        password (r/atom "")]
    (fn []
      [:div#login-panel.container-fluid
        (c/in username "text" "login-user-input" "username")
        (c/in password "password" "login-pass-input" "password")
        (c/submit "login-user-submit" "Log In"  
                  #(login-handler username password))])))

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
        password2 (r/atom "")
        phone    (r/atom "")
        email    (r/atom "")]
    (fn [] 
      [:div#signup-panel.form-horizontal
       (c/lblcmp "username" 
         (c/in username "text" "signup-user-input" "username"))
       [:div.col-xs-6.nopad
        (c/lblcmp "password" 
          (c/in password "password" "signup-pass-input" "password"))]
       [:div.col-xs-6.nopad 
        (c/lblcmp "repeat password"
          (c/in password2 "password" "signup-pass2-input" "repeat password"))]
       (c/lblcmp "phone number" 
         (c/in phone "tel" "signup-phone-input" "phone-number"))
       (c/lblcmp "email address"
         (c/in email "email" "signup-email-input" "email address"))
       (c/submit "signup-submit" "Submit User Info" #(signup-handler username password phone email))])))

; Driver Signup
;   Own name 
;   Company of Employment 
;     dropdown of companies
; FOR LATER:
;   references 
;   picture
(defn driver-signup-handler [real-name company] 
  (js/console.log "real-name: " @real-name "\ncompany  : " @company))

(defn driver-signup-panel []
  (let [real-name (r/atom "")
        company (r/atom "")]
    (fn []
      [:div#driver-signup-panel.form-horizontal
       (c/lblcmp "Real Name" 
         (c/in real-name "text" "driver-signup-name" "Real Name"))
       (c/lblcmp "Company name"
         (c/in company "text" "driver-signup-company" "Company"))
       (c/submit "driver-signup-submit" "Submit Driver Info" #(driver-signup-handler real-name company))])))

; Logged in: Customer idle

;  ride-request panel
;    [price start (optional: description end)]
;      submit-button
(defn hail-handler [price origin dest num-ppl deets]
  (js/console.log "price: " price "\norigin: " origin "\ndest: " dest "\nnum-ppl: " num-ppl "\ndeets: " deets))

(defn hail-a-cab [] 
  (let [price (r/atom 20)
        origin (r/atom "start address")
        destination (r/atom "destination address")
        num-people (r/atom 2)
        details (r/atom "")
        ]
    (fn []
      [:div.container-fluid
        [:div.form-horizontal 
          ; offer price per person
          (c/lblcmp "price" 
              (c/in price "number" "hail-price-input" @price))
          ; location of origin
          (c/lblcmp "origin"
              (c/in origin "text" "hail-origin-input" @origin))
          ; destination
          (c/lblcmp "destination"
              (c/in destination "text" "hail-destination-input" @destination))
          ; # people 
          (c/lblcmp "# of people"
              (c/in num-people "number" "hail-numppl-input" @num-people))
          ; ("2 or 3 is cozy, 4 is a squeeze")
          (c/lblcmp "details"
              (c/in details "number" "hail-details-input" @details))
          (c/submit "hail-submit" "Find a cab" #(hail-handler @price @origin @destination @num-people @details))]])))

(defn reserve-a-ride []
  (let [price        (r/atom 20)
        start-time   (r/atom 0)
        end-time     (r/atom 0)
        origin (r/atom false)
        end-point    (r/atom false)
        num-people   (r/atom 2)
        details  (r/atom "pedicab ride")]
    (fn []
      [:div.container-fluid 
        [:div.form-horizontal 
          (c/lblcmp "price"
           (c/lbl-range price "request-price-input" 15 100))
          
          (c/lblcmp "start time"
           (c/time-field start-time "request-start-time" (js/Date.)))

          (c/lblcmp "end time"
           (c/time-field end-time "request-start-time" (js/Date.)))
          ; origin
          (c/lblcmp "origin"
              (c/in origin "text" "request-origin-input" @origin))
          ; destination (optional)
          (c/lblcmp "destination"
              (c/in origin "text" "request-destination-input" @origin))
          ; # passengers
          ; # people 
          (c/lblcmp "# of people"
              (c/in num-people "number" "hail-numppl-input" @num-people))
          ; notes (required if destination is blank)
          (c/lblcmp "details"
              (c/in details "number" "hail-details-input" @details))]])))


(defn offer [co dr price desc start finish]
  [:div.container-fluid.offer
   [:div.col-xs-6 
     ; company 
    [:div.co (c/link co "#")]
     ; driver
    [:div.dr (c/link dr "#")]
     ;  offer-start-time, offer-finish-time
    [:div [:u "from " start " to " finish]]
    ]
   [:div.col-xs-6
    ; price
    [:h3 price]
   ; description
    [:p desc]
   ]])

;  active offers
(defn offer-list [] 
  [:ul.offer-list
   [:li (offer "Rogue Pedicab" "Max Jackson" "$20" "1 mile ride" "12pm" "12am")]
   [:li (offer "Rogue Pedicab" "Max Jackson" "$20" "1 mile ride" "12pm" "12am")]
   ; for-each offer [:li (offer ...)]
  ])

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
      [:div#demo.col-xs-8
       [:h2 "List of components so far" ]
       (demo-panel "login-panel" login-panel)
       (demo-panel "signup-panel" signup-panel)
       (demo-panel "driver-signup-panel" driver-signup-panel)
       (demo-panel "ride-request-panel" reserve-a-ride)
       (demo-panel "hail-a-cab-panel" hail-a-cab)
       (demo-panel "offers-list-panel" offer-list)])))
