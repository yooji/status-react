(ns status-im.components.icons.vector-icons
  (:require-macros [status-im.utils.slurp :refer [slurp]])
  (:require [reagent.core :as r]
            [status-im.utils.platform :refer [ios?]]
            [status-im.components.styles :as common]))

(def react-native-vector-icons (js/require "react-native-vector-icons"))

(def conf (.parse js/JSON (slurp "resources/fontello-config.json")))

(def icon-class (r/adapt-react-class (.createIconSetFromFontello react-native-vector-icons conf)))

(defn icon
  ([n] (icon n :dark))
  ([n color]
   [icon-class {:name (keyword (str "icon_" (name n)))
                :color (case color
                         :dark  common/icon-dark-color
                         :gray  common/icon-gray-color
                         :blue  common/color-light-blue
                         :white common/color-white
                         :red   common/icon-red-color)
                :size 24}]))

(defn options-icon []
  [icon (if ios? :dots_horizontal_dark :dots_vertical_dark) :gray])