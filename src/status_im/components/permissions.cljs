(ns status-im.components.permissions
  (:require [taoensso.timbre :as log]))

(def permissions-class (.-PermissionsAndroid js/ReactNative))

(def permissions-map
  {:read-external-storage  "android.permission.READ_EXTERNAL_STORAGE"
   :write-external-storage "android.permission.WRITE_EXTERNAL_STORAGE"
   :read-contacts          "android.permission.READ_CONTACTS"
   :camera                 "android.permission.CAMERA"
   :receive-sms            "android.permission.RECEIVE_SMS"})

(defn request-permissions [permissions then else]
  (let [permissions (mapv #(get permissions-map %) permissions)
        result      (.requestMultiple permissions-class (clj->js permissions))
        result      (.then result #(if %
                                     (then)
                                     (when else (else))))
        result      (.catch result #(then))]))