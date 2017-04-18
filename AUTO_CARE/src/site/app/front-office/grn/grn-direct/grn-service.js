(function () {
    'use strict';

    var service = function (systemConfig, $http) {

        //load pending jobcards
        this.loadItems = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/item/stock-item");
        };
        
        this.loadSupplier = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/supplier");
        };

        this.saveGrn = function (data) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/grn/save-grn", data);
        };
//        this.loadVehicles = function () {
//            return $http.get(systemConfig.apiUrl + "/api/care-point/service/zmaster/vehicle");
//        };
//
//        this.loadItemUnits = function () {
//            return $http.get(systemConfig.apiUrl + "/api/care-point/master/item-unit");
//        };
//
//        //load pending jobcards
//        this.pendingJobCards = function () {
//            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/job-card/get-pending-job-cards");
//        };
//
//        this.getPackageItems = function (indexNo) {
//            return $http.get(systemConfig.apiUrl + "/api/care-point/master/package-item/get-package-items/" + indexNo);
//        };
//
//
//        this.deleteJobItems = function (indexNo) {
//            return $http.delete(systemConfig.apiUrl + "/api/care-point/transaction/job-item/delete-job-items/" + indexNo);
//        };
//
//        this.getJobItemHistory = function (indexNo) {
//            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/job-item/find-by-job-card-items/" + indexNo);
//        };
    };

    angular.module("appModule")
            .service("GrnService", service);
}());
