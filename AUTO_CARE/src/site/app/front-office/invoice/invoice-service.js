(function () {
    'use strict';

    var service = function (systemConfig, $http) {

        //load pending jobcards
        this.pendingJobCards = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/job-card/get-invoice-pending-job-card2");
        };

        this.loadVehicle = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/zmaster/vehicle");
        };

        this.loadClient = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/zmaster/client");
        };

        this.loadEmployee = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/employee");
        };

        this.loadBank = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/zmaster/bank");
        };

        this.loadCardType = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/zmaster/card_type");
        };

        this.loadBranch = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/zmaster/branch");
        };

        this.loadPriceCategory = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/priceCategory");
        };

        this.loadBranchByBank = function (bank) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/zmaster/find-by-branch/" + bank);
        };

        this.getJobItemHistory = function (indexNo) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/job-item/find-by-job-card-items/" + indexNo);
        };

        //invoice save
        this.saveInvoice = function (data) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/jobcard-invoice/save-invoice", data);
        };

        //get client over payments
        this.getClientOverPayment = function (client) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/payment-voucher/get-client-over-payment/" + client);
//            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/jobcard-invoice/client-get-over-payment/" + client);
        };

        //get client balance
        this.getClientBalance = function (client) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/payment-voucher/get-client-balance/" + client);

        };

        this.loadInvoiceData = function (invoiceNumber) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/jobcard-invoice/get-invoice-details/" + invoiceNumber);
        };

        //vehicle select get job card
        this.getInvoiceByJobCard = function (indexNo) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/transaction/jobcard-invoice/find-by-job-card/" + indexNo);
        };
        
        //customer type goverment/privet
        this.getCustomerTypes = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/zmaster/customer-type");
        };
        this.getVehicleTypes = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/vehicle-type");
        };
        this.getVehicleBrands = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/zmaster/vehicle/get-brand-list");
        };
        this.getVehicleModels = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/zmaster/vehicle/get-model-list");
        };
        this.getPriceCategory = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/priceCategory");
        };
        this.getFuelType = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/service/zmaster/vehicle/get-fuel-type-list");
        };
        //register Customer with save
        this.registerCustomer = function (client) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/master/client/register-client",client);
        };
        this.registerVehicle = function (client) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/master/vehicle/register-detail",client);
        };
        

        //invoice viewer and print
        this.listParameters = function (report) {
            return $http.post(systemConfig.apiUrl + "/api/v1/report/report-viewer/report-parameters", JSON.stringify(report));
        };

        this.reportData = function (reportName) {
            return $http.get(systemConfig.apiUrl + "/api/v1/report/report-viewer/invoice-report-data/" + reportName);
        };

        this.getReportUrl = function (report, params, reportValues) {
            var url = systemConfig.apiUrl + "/api/v1/report/report-viewer/report";

            var action = btoa(report.fileName);
            url = url + "?action=" + action;

            angular.forEach(reportValues, function (value, key) {
                url = url + "&" + key + "=" + value;
            });

            return url;
        };

        this.viewReport = function (report, params, reportValues) {
            return $http.get(this.getReportUrl(report, params, reportValues), {responseType: 'arraybuffer'});
        };

    };

    angular.module("appModule")
            .service("invoiceService", service);
}());