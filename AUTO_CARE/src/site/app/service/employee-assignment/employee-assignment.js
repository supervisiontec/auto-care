(function () {
//module
    angular.module("employeeAssignmentModule", ['ui-notification']);
    //http factory
    angular.module("employeeAssignmentModule")
            .factory("employeeAssignmentFactory", function ($http, systemConfig) {
                var factory = [];
                //load Jobs
                factory.loadEmployees = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/employee/worker";
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {

                            });
                };
                factory.getAssignEmployees = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/employee-assignment/get-assign-employees";
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {

                            });
                };

                factory.employeeImageAbsalutePath = function (callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/employee-assignment/file-absalute-path";
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                errorcallback(data);
                            });
                };

                factory.employeesAttendance = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/employee-attendance";
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {

                            });
                };
                //load Bays
                factory.loadBays = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/bay/get-bays-by-branch-employee-is-view";
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {

                            });
                };
                //load Vehicles
                factory.loadVehicles = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/vehicle";
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {

                            });
                };
                //insert 
                factory.insertDetail = function (detail, callback, errorcallback) {

                    var url = systemConfig.apiUrl + "/api/care-point/transaction/employee-assignment/insert-detail";
                    $http.post(url, detail)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                if (errorcallback) {
                                    errorcallback(data);
                                }
                            });
                };
                //get Bay Assign Vehicle Count
                factory.getBayAssignEmployeeCount = function (detail, callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/employee-assignment/bay-employee-count/" + detail;
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                if (errorcallback) {
                                    errorcallback(data);
                                }
                            });
                };
                factory.getImageByNameAndIndexNO = function (name, indexNo, callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/employee/image-names/" + name + "/" + indexNo;
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                if (errorcallback) {
                                    errorcallback(data);
                                }
                            });
                };
                factory.reset = function (callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/employee-assignment/reset-employees";
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                if (errorcallback) {
                                    errorcallback(data);
                                }
                            });
                };
                factory.outEmployee = function (employeeIndex, callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/employee-assignment/out-employee/" + employeeIndex;
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {
                                if (errorcallback) {
                                    errorcallback(data);
                                }
                            });
                };
                return factory;
            });
    //controller
    angular.module("employeeAssignmentModule")
            .controller("employeeAssignmentController", function ($scope, $timeout, ConfirmPane, $filter, employeeAssignmentFactory, Notification, systemConfig) {
                $scope.ui = {};
                $scope.model = {};
                $scope.http = {};
                $scope.imagemodelX = [];
                $scope.model.employeeAssignment = {
                    "bay": {
                        timeout: null
                    },
                    "employee": null
                };

                $scope.model.jobAssignmentList = [];
                $scope.model.employeeList = [];
                $scope.model.employeesAttendanceList = [];
                $scope.model.vehicles = [];
                $scope.dragableMode = true;
                $scope.model.imageAbsalutePath = null;
                $scope.model.assignEmployeeList = [];
                $scope.selectedEmployee = -1;
                $scope.model.bayList = [
                    {
                        timeout: null
                    }
                ];
//
                $scope.stop = function (bay) {
                    bay.timeout = '';
//                    $timeout.cancel(mytimeout);

                };
                $scope.dragStart = function (element, model) {
                };

                $scope.dragLeave = function (bay, empAssingnment) {
                    var vehicleCount = 0;
                    employeeAssignmentFactory.getBayAssignEmployeeCount(bay.indexNo,
                            function (data) {
                                vehicleCount = data;
                                $scope.isSameBay = false;
                                if (bay.indexNo === empAssingnment.bay) {
                                    $scope.isSameBay = true;
                                }
                                if ($scope.isSameBay) {
                                    $scope.model.jobAssignment.bay.timeout = '';
                                    Notification.error('Same Bay Assing ...');
                                } else {

                                    if (vehicleCount < bay.maxEmployee) {
                                        $scope.model.employeeAssignment.employee = empAssingnment.employee;
                                        $scope.model.employeeAssignment.bay = bay;
                                        $scope.model.employeeAssignment.bay.timeout = 1;

                                        $scope.onTimeout($scope.model.employeeAssignment.bay);
                                    } else {
                                        Notification.error('Max Employee Assign for ' + bay.name + '  !');
                                    }
                                }
                            });
//                    
                };
                $scope.getEmployee = function (employee) {
                    for (var i = 0; i < $scope.model.employeeList.length; i++) {
                        if ($scope.model.employeeList[i].indexNo === employee) {
                            return $scope.model.employeeList[i];
                            break;
                        }
                    }
                };
                $scope.getBay = function (bay) {
                    for (var i = 0; i < $scope.model.bayList.length; i++) {
                        if ($scope.model.bayList[i].indexNo === bay) {
                            return $scope.model.bayList[i];
                            break;
                        }
                    }
                };
                $scope.onTimeout = function (bay) {
                    if ($scope.model.employeeAssignment.bay.timeout !== null) {

                        if ($scope.model.employeeAssignment.bay.timeout !== '') {
                            var mytimeout = $timeout($scope.onTimeout, 1000);
                            $scope.model.employeeAssignment.bay.timeout--;

                            if ($scope.model.employeeAssignment.bay.timeout === 0) {
                                $timeout.cancel(mytimeout);
                                $scope.http.insertDetail();
                            }
                        } else {
                            $scope.model.employeeAssignment.bay.timeout = '';
                        }
                    } else {
                        Notification.error('Some Error. Please Refresh this Window.. !');
                    }
                };

                //all employee assign waiting bay
                $scope.ui.reset = function () {
                    ConfirmPane.warningConfirm("Do you want to finish all employees work ?  ")
                            .confirm(function () {
                                employeeAssignmentFactory.reset(function (data) {
                                    if (data) {
                                        Notification.success("all employees assigned waiting bay successfully ");
                                        $scope.http.getAssignEmployees();
                                    }
                                });
                            });
                };

                $scope.http.insertDetail = function () {
                    $scope.model.employeeAssignment.inTime = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss');
                    $scope.model.employeeAssignment.outTime = null;
                    $scope.model.employeeAssignment.bay = $scope.model.employeeAssignment.bay.indexNo;
                    var detail = $scope.model.employeeAssignment;
                    var detailJSON = JSON.stringify(detail);
                    //save detail dirrectly
                    if ($scope.model.employeeAssignment.bay) {
                        if ($scope.model.employeeAssignment.employee) {
                            employeeAssignmentFactory.insertDetail(
                                    detailJSON,
                                    function (data) {

                                        var bay = $scope.getBay(data.bay);
                                        $scope.http.getAssignEmployees();
                                        Notification.success("Employee " + " " + $scope.getEmployee(data.employee).name + " " + "Assign" + "  " + bay.name + "  " + "Success !");
                                    }
                            , function (data) {
                                Notification.error(data);

                            });
                        } else {
                            Notification.error('Select a employee to Transfer');
                        }
                    } else {
                        Notification.error('Select a Bay to Transfer');
                    }


                };
                $scope.ui.downloardImage = function () {
                    for (var i = 0; i < $scope.model.employeeList.length; i++) {
                        $scope.model.employeeList[i].imageData = systemConfig.apiUrl + "/api/care-point/master/employee/download-image/" + $scope.model.employeeList[i].image;
                    }
                };

                $scope.http.loadEmployees = function () {
                    employeeAssignmentFactory.loadEmployees(function (data) {
                        $scope.model.employeeList = data;
                        $scope.ui.downloardImage();
                    });
                };
                $scope.http.getAssignEmployees = function () {
                    employeeAssignmentFactory.getAssignEmployees(function (data) {
                        $scope.model.assignEmployeeList = data;
                        console.log("assign employee list");
                        console.log(data);
                    });
                };
                $scope.ui.refresh = function () {
                    console.log("refresh");
                    $scope.ui.init();
                };
                $scope.ui.outEmployee = function () {
                    if ($scope.selectedEmployee !== -1) {
                        ConfirmPane.warningConfirm("Do you want to out selected employee ?")
                                .confirm(function () {
                                    employeeAssignmentFactory.outEmployee($scope.selectedEmployee, function (data) {
                                        Notification.success('selected employee out successfully !');
                                        $scope.http.getAssignEmployees();
                                        $scope.selectedEmployee === -1;
                                    });

                                });
                    } else {
                        Notification.error('select a employee to out !');
                    }
                };
                $scope.ui.selectedEmployee = function (employeeIndex) {
                    $scope.selectedEmployee = employeeIndex;
                    console.log($scope.selectedEmployee);
                };

                $scope.ui.init = function () {

                    $scope.http.loadEmployees();

                    $scope.http.getAssignEmployees();

                    employeeAssignmentFactory.employeesAttendance(function (data) {
                        $scope.model.employeesAttendanceList = data;
                    });

                    employeeAssignmentFactory.loadBays(function (data) {
                        $scope.model.bayList = data;
                    });

                    employeeAssignmentFactory.loadVehicles(function (data) {
                        $scope.model.vehicles = data;
                    });
                    employeeAssignmentFactory.employeeImageAbsalutePath(function (data) {
                        $scope.model.imageAbsalutePath = data.imagePath;
                    });

                };
                $scope.ui.init();
            });
}());
