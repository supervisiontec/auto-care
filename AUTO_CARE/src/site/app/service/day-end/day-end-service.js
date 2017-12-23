(function () {
    'use strict';

    var service = function (systemConfig, $http) {

        //load pending jobcards
        this.loadNumberMap = function (date) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/dat-end/load-number-map/"+date);
        };
        this.save = function (date) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/dat-end/save/",date);
        };
        
    };

    angular.module("appModule")
            .service("dayEndService", service);
}());