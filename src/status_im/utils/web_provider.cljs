(ns status-im.utils.web-provider
  (:require [taoensso.timbre :as log]
            [status-im.components.status :as status]))

(defn RNBridgeProvider [rpc-url]
  (this-as provider
    (set! (.-host provider) rpc-url)
    provider))

(set! (.. RNBridgeProvider -prototype -sendAsync)
      (fn [payload callback]
        (this-as provider
          (status/call-web3
            (.-host provider)
            (.stringify js/JSON payload)
            (fn [response]
              (if (= "" response)
                (log/warn :web3-response-error)
                (callback nil (.parse js/JSON response))))))))
