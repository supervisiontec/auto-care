(function () {
    var factory = function (vehicleEntranceService, vehicleEntranceFactory, $q, $filter) {
        function vehicleEntranceModel() {
            this.constructor();
        }

        vehicleEntranceModel.prototype = {
            //data model
            vehicleData: {},
            clientData: {},
            jobcard: {},
            vehicleTypeData: {},
            priceCategoryData: {},
            //searchBox
            searchKeyword: null,
            showSuggestions: false,
            searchSuggestions: [],
            //uib-typeHead
            vehicleList: [],
            clientList: [],
            customerTypeList: [],
            vehicleTypeList: [],
            priceCategoryList: [],
            vehicleAttenctionsCategoryList: [],
            vehicleAttenctionsList: [],
            jobCardList: [],
            jobCardHistryList: [],
            lastJobCardVehicleAttenctionList: [],
            brandList: [],
            modelList: [],
            jobHistory: [],
            constructor: function () {
                var that = this;
                this.vehicleData = vehicleEntranceFactory.newVehicleData();
                this.clientData = vehicleEntranceFactory.newClientData();
                this.jobcard = vehicleEntranceFactory.newJobCardData();
                this.vehicleTypeData = vehicleEntranceFactory.newVehicleTypeData();
                this.priceCategoryData = vehicleEntranceFactory.newPriceCategoryData();

                this.loadVehicle();
                vehicleEntranceService.loadVehicleType()
                        .success(function (data) {
                            that.vehicleTypeList = data;
                        });
                vehicleEntranceService.loadCustomerType()
                        .success(function (data) {
                            that.customerTypeList = data;
                        });
                vehicleEntranceService.loadPriceCategory()
                        .success(function (data) {
                            that.priceCategoryList = data;
                        });
                vehicleEntranceService.getVehicleAttenctionsCategory()
                        .success(function (data) {
                            that.vehicleAttenctionsCategoryList = data;
                        });
                vehicleEntranceService.getVehicleAttenctions()
                        .success(function (data) {
                            that.vehicleAttenctionsList = data;
                        });
                vehicleEntranceService.getBrandList()
                        .success(function (data) {
                            that.brandList = data;
                        });
                vehicleEntranceService.getModelList()
                        .success(function (data) {
                            that.modelList = data;
                        });
            },
            loadJobCardByClientIndexNo: function (indexNo) {
                var that = this;
                vehicleEntranceService.getJobCard(indexNo)
                        .success(function (data) {
                            that.jobCardList = data;
                        });
            },
            loadVehicle: function () {
                var that = this;
                vehicleEntranceService.loadVehicle()
                        .success(function (data) {
                            that.vehicleList = data;
                        });
            },
            getJobHistory: function (vehicleNo) {
                var that = this;
                console.log("vehicleNo");
                that.jobHistory=[];
               vehicleEntranceService.getJobHistory(vehicleNo)
                        .success(function (dataList) {
                            console.log(dataList);
                            angular.forEach(dataList,function (data){
                                data.expanded=false;
                                that.jobHistory.push(data);
                                
                            });
                        });
            },
            clearModel: function () {
                this.clientData = {};
                this.vehicleData = {};
                this.jobcard = {};
                this.vehicleTypeData = {};
                this.priceCategoryData = {};
            },
            clear: function () {
                this.clientData = {};
            },
            vehicleSerachByIndex: function (indexNo) {
                var that = this;
                vehicleEntranceService.vehicleSerachByIndex(indexNo)
                        .success(function (data) {
                            that.vehicleData = data;
                            vehicleEntranceService.getClientByIndexNo(that.vehicleData.client)
                                    .success(function (data) {
                                        that.clientData = data;
                                    })
                                    .error(function () {
                                        console.log("error search");
                                    });
                        })
                        .error(function () {
                            console.log("error search");
                        });
            },
            findByVehicleNo: function () {
                var that = this;
                vehicleEntranceService.findByVehicleNo(this.searchKeyword)
                        .success(function (data) {
                            that.searchSuggestions = data;
                        })
                        .error(function () {
                            that.searchSuggestions = [];
                        });
            },
            clientSearchByClientNo: function () {
                var that = this;
                console.log("ABCD");
                console.log(this.vehicleData.client);
                vehicleEntranceService.getClientByIndexNo(this.vehicleData.client)
                        .success(function (data) {
                            that.clientData = data;
                            var mobile = parseInt(data.mobile);
                            that.clientData.mobile = mobile;
                            console.log(data);
                        });
            },
            searchPendingJobCard: function (VehicleNo) {
                var that = this;
                var defer = $q.defer();
                vehicleEntranceService.searchPendingJobCard(VehicleNo)
                        .success(function (data) {
                            defer.resolve(data);
                        })
                        .error(function () {
                            defer.reject();
                        });
                return defer.promise;
            },
            saveJobCard: function () {
                var that = this;
                that.jobcard.transaction = 1;
                that.jobcard.status = "PENDING";
                that.jobcard.bay = 1;
                that.jobcard.serviceChagers = 0;
                that.jobcard.priceCategory = that.vehicleData.priceCategory;
                that.jobcard.inMileage = that.vehicleData.lastMilage;
                that.jobcard.client = that.clientData.indexNo;
                that.jobcard.vehicle = that.vehicleData.indexNo;
                var defer = $q.defer();
                console.log(that.jobcard);
                vehicleEntranceService.saveJob(JSON.stringify(that.jobcard))
                        .success(function (data) {
//                            that.loadClient();
//                            that.loadVehicle();
                            defer.resolve(data);
                        })
                        .error(function () {
                            defer.reject();
                        });
                return defer.promise;
            },
            updateClientFromVehicle: function () {
                var that = this;
                this.vehicleData.client = that.clientData.indexNo;
                this.vehicleData.type = "NORMAL";

                if (angular.isUndefined(this.vehicleData.isNew)) {
                    this.vehicleData.isNew = 1;
                }
                if (angular.isUndefined(this.vehicleData.date)) {
                    this.vehicleData.date = $filter('date')(new Date(), 'yyyy-MM-dd');
                }
                var defer = $q.defer();
                vehicleEntranceService.updateVehicle(JSON.stringify(that.vehicleData))
                        .success(function (data) {
                            that.vehicleData = data;
                            that.clientList.unshift(data);
                            defer.resolve();
                        })
                        .error(function () {
                            defer.reject();
                        });
                return defer.promise;
            },
            newClient: function () {
                var that = this;
                var defer = $q.defer();
                that.clientData.type = "NEW";
                if (!that.clientData.customerType) {
                    that.clientData.customerType = 2;
                }
                that.clientData.date = $filter('date')(new Date(), 'yyyy-MM-dd');
                that.clientData.isNew = true;

                vehicleEntranceService.newClient(JSON.stringify(that.clientData))
                        .success(function (data) {
                            that.clientData = data;
                            that.vehicleList.unshift(data);
                            defer.resolve();
                        })
                        .error(function () {
                            defer.reject();
                        });
                return defer.promise;
            },
            updateClient: function () {
                var that = this;
                var defer = $q.defer();
                vehicleEntranceService.newClient(JSON.stringify(that.clientData))
                        .success(function (data) {
                            that.clientData = data;
                            defer.resolve();
                        })
                        .error(function () {
                            defer.reject();
                        });
                return defer.promise;
            },
            vehicleLabel: function (indexNo) {
                var vehicle = "";
                angular.forEach(this.vehicleList, function (value) {
                    if (value.indexNo === parseInt(indexNo)) {
                        vehicle = value.vehicleNo;
                        return;
                    }
                });
                return vehicle;
            },
            vehicleLabelByNumber: function (no) {
                var vehicle = "";
                angular.forEach(this.vehicleList, function (value) {
                    if (value.vehicleNo === no) {
                        vehicle = value.vehicleNo;
                        return;
                    }
                });
                return vehicle;
            },
            vehicle: function (vehicleNo) {
                var data;
                angular.forEach(this.vehicleList, function (value) {
                    if (angular.equals(value.vehicleNo, vehicleNo)) {
                        data = value;
                        return;
                    }
                });
                return data;
            },
            clientLabel: function (indexNo) {
                var client = "";
                angular.forEach(this.clientList, function (value) {
                    if (value.indexNo === parseInt(indexNo)) {
                        client = value.name;
                        return;
                    }
                });
                return client;
            },
            clientType: function (indexNo) {
                var data = "";
                angular.forEach(this.customerTypeList, function (value) {
                    if (value.indexNo === parseInt(indexNo)) {
                        data = value;
                        return;
                    }
                });
                return data;
            },
            vehicleTypeLabel: function (indexNo) {
                var vehicleType = "";
                angular.forEach(this.vehicleTypeList, function (value) {
                    if (value.indexNo === parseInt(indexNo)) {
                        vehicleType = value.type;
                        return;
                    }
                });
                return vehicleType;
            },
            priceCategoryLabel: function (indexNo) {
                var priceCategory = "";
                angular.forEach(this.priceCategoryList, function (value) {
                    if (value.indexNo === parseInt(indexNo)) {
                        priceCategory = value.name;
                        return;
                    }
                });
                return priceCategory;
            },
            vehicleAttenctions: function (indexNo) {
                var data = "";
                angular.forEach(this.vehicleAttenctionsList, function (values) {
                    if (values.indexNo === indexNo) {
                        data = values;
                        return;
                    }
                });
                return data;
            },
            vehicleAttenctionCategory: function (indexNo) {
                var data = "";
                angular.forEach(this.vehicleAttenctionsCategoryList, function (values) {
                    if (values.indexNo === parseInt(indexNo)) {
                        data = values;
                        return;
                    }
                });
                return data;
            },
            getLastJobCardVehicleAttenctions: function (jobCard) {
                var that = this;
                var defer = $q;
                vehicleEntranceService.getLastJobCardVehicleAttenctions(jobCard)
                        .success(function (data) {
                            that.lastJobCardVehicleAttenctionList = [];
                            that.lastJobCardVehicleAttenctionList = data;
                            defer.resolve();
                        })
                        .error(function () {
                            defer.reject();
                        });
                return defer.promise;
            },
            clientVehicles: function (indexNo) {
                var defer = $q;
                vehicleEntranceService.vehicleSearchByClient(indexNo)
                        .success(function (data) {
                            defer.resolve(data);
                        })
                        .error(function () {
                            defer.reject();
                        });
                return defer.promise;
            },
            getVehicleHistory: function (vehicleNo) {
                var that = this;
                vehicleEntranceService.getVehicleHistory(vehicleNo)
                        .success(function (data) {
                            that.jobCardHistryList = data;
                        })
                        .error(function () {

                        });
            },
            searchClientByNameMobile: function (name, mobile) {
                var that = this;
                vehicleEntranceService.getClientByNameMobile(name, mobile)
                        .success(function (data) {
                            that.clientList = data;
                        })
                        .error(function () {

                        });
            }
        };
        return vehicleEntranceModel;
    };
    angular.module("appModule")
            .factory("vehicleEntranceModel", factory);
}());