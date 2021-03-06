(function () {
    //module
    angular.module("vehicleTypeModule", ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'ui-notification']);

    //http factory
    angular.module("vehicleTypeModule")
            .factory("vehicleTypeFactory", function ($http, systemConfig) {
                var factory = {};

                // lord factory
                factory.lordVehicleType = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/vehicle-type";

                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };

                factory.lordPriceCategory = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/priceCategory";

                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                callback(data);
                            });
                };

                factory.saveVehicleTypeFactory = function (summary, callback, errorCallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/vehicle-type/insert-detail";

                    $http.post(url, summary)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                if (errorCallback) {
                                    errorCallback(data);
                                }
                            });
                };

                factory.deleteVehicleTypeFactory = function (indexNo, callback, errorCallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/vehicle-type/delete-detail/" + indexNo;

                    $http.delete(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                errorCallback(data);
                            });
                };


                return factory;
            });


    //controller
    angular.module("vehicleTypeModule")
            .controller("vehicleTypeController", function ($scope, vehicleTypeFactory, Notification, $timeout) {
                //data models 
                $scope.model = {};

                //ui models
                $scope.ui = {};

                //http models
                $scope.http = {};

                //current ui mode IDEAL, SELECTED, NEW, EDIT
                $scope.ui.mode = null;

                //------------------ model functions ---------------------------
                //reset model
                $scope.model.reset = function () {
                    $scope.model.vehicleType = {};
                };

                //------------------ validation functions ------------------------------

                $scope.validateInput = function () {
                    if ($scope.model.vehicleType.model) {
                        return true;
                    } else {
                        return false;
                    }
                };

                //<-----------------http funtiion------------------->
                $scope.http.saveVehicleType = function () {
                    var detail = $scope.model.vehicleType;
                    var detailJSON = JSON.stringify(detail);
                    //save
                    console.log(detailJSON);
                    vehicleTypeFactory.saveVehicleTypeFactory(
                            detailJSON,
                            function (data) {
                                Notification.success("success");
                                $scope.model.vehicleTypeList.push(data);
                                $scope.model.reset();
                                $scope.ui.focus();
                            },
                            function (data) {
                                Notification.error(data.message);
                            }
                    );
                };

                $scope.http.deleteVehicleType = function (indexNo, index) {
                    vehicleTypeFactory.deleteVehicleTypeFactory(indexNo, function () {
                        $scope.model.vehicleTypeList.splice(index, 1);
                        Notification.success("delete success");
                    });
                };

                //<-----------------ui funtiion--------------------->
                //save function
                $scope.ui.save = function () {
                    if ($scope.validateInput()) {
                        $scope.http.saveVehicleType();
                    } else {
                        Notification.error("please input detail");
                    }
                };

                //focus
                $scope.ui.focus = function () {
                    $timeout(function () {
                        document.querySelectorAll("#model")[0].focus();
                    }, 10);
                };

                //new function
                $scope.ui.new = function () {
                    $scope.ui.mode = "NEW";
                    $scope.ui.focus();
                };

                //edit funtion
                $scope.ui.edit = function (vehicleTypes, index) {
                    $scope.ui.mode = "EDIT";
                    $scope.model.vehicleType = vehicleTypes;
                    $scope.model.vehicleTypeList.splice(index, 1);

                    $scope.ui.focus();
                };

                $scope.priceCategoryLable = function (indexNo) {
                    var priceCategory = "";
                    angular.forEach($scope.model.priceCategoryList, function (value) {
                        if (value.indexNo === indexNo) {
                            priceCategory = value.name;
                            return;
                        }
                    });
                    return priceCategory;
                };


                $scope.ui.init = function () {
                    //set ideal mode
                    $scope.ui.mode = "IDEAL";

                    //reset mdel
                    $scope.model.reset();

                    vehicleTypeFactory.lordPriceCategory(function (data) {
                        $scope.model.priceCategoryList = data;
                    });

                    //load vehicle type
                    vehicleTypeFactory.lordVehicleType(function (data) {
                        $scope.model.vehicleTypeList = data;
                    });
                };

                $scope.ui.init();
            });
}());