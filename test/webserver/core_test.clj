(ns webserver.core-test
  (:require [clojure.test :refer :all]
            [org.httpkit.client :as http]
            [webserver.core :as webserver]
            [clojure.data.json :as json]))

(defn read-json-body [response]
  (json/read-str (:body @response) :key-fn keyword))

(use-fixtures :once (fn [f]
                      (let [server (webserver/start {})]
                        (try (f) (finally (server))))))


;;; TESTS ;;;

(deftest navigation-nodes-test
  (testing "Should only return entries whose product count is greater than zero"
    (is (= {:id             "0"
            :title          "Root page"
            :product-count  1
            :internal-score 100
            :children       [{:id             "1"
                              :title          "Women"
                              :description    "This is a tiny description example"
                              :product-count  100
                              :internal-score 30
                              :children       [{:id             "10"
                                                :title          "Pants"
                                                :product-count  1
                                                :internal-score 20
                                                :children       [{}]}
                                               {:id             "11"
                                                :title          "Bags"
                                                :product-count  20
                                                :internal-score 5
                                                :children       [{}]}
                                               {:id             "13"
                                                :title          "Shirts"
                                                :product-count  1000
                                                :internal-score 99
                                                :children       [{:id             "130"
                                                                  :title          "Long sleeved"
                                                                  :product-count  100
                                                                  :internal-score 1
                                                                  :children       [{}]}]}]}
                             {:id            "3"
                              :title         "Men"
                              :product-count 10
                              :description   "This is another description example"
                              :children      [{}]}
                             {:id             "4"
                              :title          "Children"
                              :product-count  40
                              :internal-score 10
                              :children       [{}]}
                             {:id             "5"
                              :title          "Sports"
                              :product-count  100
                              :internal-score 1
                              :children       [{:id             "50"
                                                :title          "Boots"
                                                :product-count  1
                                                :internal-score 5
                                                :description    "Description for boots"
                                                :children       [{}]}
                                               {:id             "51"
                                                :title          "Dumbbells"
                                                :product-count  20
                                                :internal-score 300
                                                :description    "This product must be great"
                                                :children       [{}]}
                                               {:id             "53"
                                                :title          "Pants"
                                                :product-count  10
                                                :internal-score 5
                                                :children       [{}]}]}
                             {:id            "6"
                              :title         "Kitchen"
                              :product-count 30
                              :children      [{}]}]}
           (read-json-body (http/get "http://127.0.0.1:8080/navigation-nodes"))))))


(deftest node-data-test
  (testing "Should return node data by id"
    (is (= {:id             "1"
            :title          "Women"
            :description    "This is a tiny description example"
            :product-count  100
            :internal-score 30
            :children       [{:id             "10"
                              :title          "Pants"
                              :product-count  1
                              :internal-score 20
                              :children       [{}]}
                             {:id             "11"
                              :title          "Bags"
                              :product-count  20
                              :internal-score 5
                              :children       [{}]}
                             {:id             "12"
                              :title          "Dresses"
                              :product-count  0
                              :internal-score 100
                              :children       [{}]}
                             {:id             "13"
                              :title          "Shirts"
                              :product-count  1000
                              :internal-score 99
                              :children       [{:id             "130"
                                                :title          "Long sleeved"
                                                :product-count  100
                                                :internal-score 1
                                                :children       [{}]}
                                               {:id             "131"
                                                :title          "Elegant"
                                                :product-count  0
                                                :internal-score 3
                                                :children       [{}]}
                                               {:id             "132"
                                                :title          "Sporty"
                                                :product-count  0
                                                :internal-score 3
                                                :children       [{}]}]}]}
           (read-json-body (http/get "http://127.0.0.1:8080/node-data/1"))))

    (is (= {:id             "51"
            :title          "Dumbbells"
            :product-count  20
            :internal-score 300
            :description    "This product must be great"
            :children       [{}]}
           (read-json-body (http/get "http://127.0.0.1:8080/node-data/51"))))))