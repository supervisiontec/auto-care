(function () {
//module
    angular.module("bayAssignmentModule", ['ui-notification']);
    //http factory
    angular.module("bayAssignmentModule")
            .factory("bayAssignmentFactory", function ($http, systemConfig) {
                var factory = {};
                //load Jobs
                factory.loadJobs = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/job-card/get-not-finished-job-cards";
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {

                            });
                };
                //load Bays
                factory.loadBays = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/bay/get-bays-by-branch-is-view";
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
                //load Vehicle  Types
                factory.loadVehicleTypes = function (callback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/vehicle-type";
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {

                            });
                };
                //insert 
                factory.insertDetail = function (detail, callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/vehicle-assignment/insert-detail";
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
                //insert 
                factory.finishJob = function (detail, callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/vehicle-assignment/job-finished";
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

                factory.checkEmployeAssign = function (detail, callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/vehicle-assignment/check-employe-assign/" + detail;
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
                factory.setStopTime = function (detail, callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/vehicle-assignment/set-stop-time";
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
                factory.getJobIsStop = function (detail, callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/vehicle-assignment/get-job-is-stop/" + detail;
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
                factory.getBayInTime = function (job, bay, callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/vehicle-assignment/get-bay-in-time/" + job + "/" + bay;
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
                factory.getServerTime = function (callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/vehicle-assignment/get-system-time";
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
                factory.getJobActivities = function (detail, bay, callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/job-item/get-job-activities/" + detail + "/" + bay;
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
                factory.getJobAllActivities = function (detail, callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/job-item/get-job-all-activities/" + detail;
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
                factory.getItems = function (callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/master/item/activity-package-item";
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
                factory.savaActivity = function (detail, callback, errorcallback) {
                    var url = systemConfig.apiUrl + "/api/care-point/transaction/job-item/save-job-activities";
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
                return factory;
            });
    //controller
    angular.module("bayAssignmentModule")
            .controller("bayAssignmentController", function ($scope, $window, $uibModalStack, $timeout, $uibModal, $filter, ConfirmPane, bayAssignmentFactory, Notification) {
                $scope.ui = {};
                $scope.model = {};
                $scope.http = {};
                $scope.model.jobAssignment = {
                    "bay": null,
                    "jobCard": null
                };

                $scope.model.jobAssignmentList = [];
                $scope.model.jobList = [];
                $scope.model.vehicles = [];
                $scope.model.vehicleTypes = [];
                $scope.dragableMode = true;
                $scope.model.selectJob = null;
                $scope.model.selectBay = null;
                $scope.model.bayList = [];
                $scope.model.refershTime = 60;
                $scope.model.refershTime = 60;
                $scope.model.itemList = [];
                $scope.model.activityList = [];
                $scope.popupDisabled = false;
//
                $scope.stop = function (bay) {
                    bay.timeout = '';
//                    $timeout.cancel(mytimeout);

                };
                $scope.dragStart = function (element, model) {
                };

                $scope.ui.selectJob = function (job) {
                    $scope.model.selectJob = job;
                    console.log(job);
                };
                $scope.ui.selectBay = function (bay) {
                    $scope.model.selectBay = bay;
                    if ($scope.model.selectBay && $scope.model.selectJob) {
                        $scope.dragLeave($scope.model.selectBay, $scope.model.selectJob);
                    }
                };


                $scope.dragLeave = function (bay, job) {
                    if ($scope.dragableMode) {

                        $scope.dragableMode = false;
                        var vehicleCount = 0;

                        for (var i = 0; i < $scope.model.jobList.length; i++) {
                            if ($scope.model.jobList[i].bay === bay.indexNo) {
                                vehicleCount++;
                            }
                        }
                        $scope.isSameBay = false;
                        for (var i = 0; i < $scope.model.jobList.length; i++) {
                            if ($scope.model.jobList[i].bay === bay.indexNo) {
                                if ($scope.model.jobList[i].vehicle === job.vehicle) {
                                    $scope.isSameBay = true;
                                    break;
                                }
                            }
                        }
                        if ($scope.isSameBay) {
                            $scope.model.jobAssignment.bay.timeout = '';
                        } else {
                            // check employee assing selected bay
                            if (bay.employeeIsView) {
                                bayAssignmentFactory.checkEmployeAssign(bay.indexNo, function (data) {
                                    if (data) {
                                        if ($scope.model.refershTime < 20) {
                                            $scope.model.refershTime += 10;
                                        }
                                        if (vehicleCount < bay.maxVehicle) {
                                            $scope.model.jobAssignment.jobCard = job;
                                            $scope.model.jobAssignment.bay = bay;
                                            $scope.openModel();
                                        } else {
                                            Notification.error('Max vehicle Assign for this bay !');
                                        }
                                    } else {
                                        Notification.error('Nothing Employee Assign for this Bay !');
                                        return;
                                    }
                                });
                            } else {
                                if ($scope.model.refershTime < 20) {
                                    $scope.model.refershTime += 10;
                                }
                                if (vehicleCount < bay.maxVehicle) {
                                    $scope.model.jobAssignment.jobCard = job;
                                    $scope.model.jobAssignment.bay = bay;
                                    $scope.model.jobAssignment.bay.timeout = 5;
                                    $scope.onTimeout();
                                } else {
                                    Notification.error('Max vehicle Assign for this bay !');
                                }
                            }
                        }
                    }
                    $scope.dragableMode = true;
                };
                $scope.getVehicle = function (vehicle) {
                    for (var i = 0; i < $scope.model.vehicles.length; i++) {
                        if ($scope.model.vehicles[i].indexNo === vehicle) {
                            return $scope.model.vehicles[i];
                            break;
                        }
                    }
                };
                $scope.getJobCard = function (jobId) {
                    for (var i = 0; i < $scope.model.jobList.length; i++) {
                        if ($scope.model.jobList[i].indexNo === jobId) {
                            return $scope.model.jobList[i];
                            break;
                        }
                    }
                };
                $scope.getBay = function (bayIndex) {
                    for (var i = 0; i < $scope.model.bayList.length; i++) {
                        if ($scope.model.bayList[i].indexNo === bayIndex) {
                            return $scope.model.bayList[i];
                            break;
                        }
                    }
                };


                $scope.doJobFinish = function (job) {
                    var jobCard = job;
                    jobCard.inTime = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss');

                    var detail = jobCard;
                    var detailJSON = JSON.stringify(detail);
                    bayAssignmentFactory.finishJob(
                            detailJSON,
                            function (data) {
                                Notification.success('Job Finished Success !');
                            }
                    , function (data) {
                        Notification.error(data.message);

                    });
                };
                $scope.openModel = function () {
                    bayAssignmentFactory.getJobActivities(
                            $scope.model.jobAssignment.jobCard.indexNo,
                            $scope.model.jobAssignment.bay.mainBay,
                            function (data) {
                                $scope.model.activityList = data;
                            });
                    $scope.popupDisabled = false;
                    $uibModal.open({
                        animation: true,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: 'jobActivities.html',
                        scope: $scope,
                        size: 'lg'
                    });
                };
                $scope.ui.modelCancel = function () {
                    $uibModalStack.dismissAll();
                };
                $scope.ui.loadItemPopup = function () {
                    bayAssignmentFactory.getJobAllActivities(
                            $scope.model.selectJob.indexNo,
                            function (data) {
                                $scope.model.activityList = data;
                            });
                    $scope.popupDisabled = true;
                    $uibModal.open({
                        animation: true,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: 'jobActivities.html',
                        scope: $scope,
                        size: 'lg'
                    });
                };
                $scope.modelOk = function () {
                    var check = false;
                    var timeToSec = 0;
                    angular.forEach($scope.model.activityList, function (activity) {
                        if (activity.setUsed) {
                            check = true;
                            activity.used = true;
                            activity.bay = $scope.model.jobAssignment.bay.indexNo;

                            var parts = activity.activityTime.split(":");
                            var itemTime = (parts[0] * 3600) +
                                    (parts[1] * 60) +
                                    (+parts[2]);
                            timeToSec += parseInt(itemTime);
                        }
                    });

                    if (check) {
                        var total = $scope.totalTime(timeToSec);
                        $scope.ui.modelCancel();
                        $scope.model.jobAssignment.bay.timeout = 5;
                        $scope.onTimeout();
                        Notification.success(total);
                    } else {
                        Notification.error('Select a activity to assing bay !');
                    }
                };
                $scope.http.jobActivitySave = function () {
                    bayAssignmentFactory.savaActivity(
                            JSON.stringify($scope.model.activityList),
                            function (data) {
                            });
                };

                $scope.totalTime = function (milisecondsDiff) {
                    return [$scope.pad(Math.floor(milisecondsDiff / 3600) % 60),
                        $scope.pad(Math.floor(milisecondsDiff / 60) % 60),
                        $scope.pad(milisecondsDiff % 60)
                    ].join(":");
                };

                $scope.pad = function (num) {
                    if (num < 10) {
                        return "0" + num;
                    } else {
                        return "" + num;
                    }
                };
                $scope.onTimeout = function () {

                    if ($scope.model.jobAssignment.bay.timeout !== '') {
                        var mytimeout = $timeout($scope.onTimeout, 1000);
                        $scope.model.jobAssignment.bay.timeout--;

                        if ($scope.model.jobAssignment.bay.timeout === 0) {
                            $timeout.cancel(mytimeout);
                            $scope.http.jobActivitySave();
                            $scope.http.insertDetail();

                        }
                    } else {
                        $scope.model.jobAssignment.bay.timeout = '';
                    }
                };

                $scope.http.insertDetail = function () {

                    $scope.model.jobAssignment.inTime = null;
                    $scope.model.jobAssignment.outTime = null;
                    $scope.model.jobAssignment.branch = 1;
                    $scope.model.jobAssignment.date = new Date();
                    $scope.model.jobAssignment.bay = $scope.model.jobAssignment.bay.indexNo;
                    $scope.model.jobAssignment.jobCard = $scope.model.jobAssignment.jobCard.indexNo;
                    var detail = $scope.model.jobAssignment;
                    var detailJSON = JSON.stringify(detail);
                    //save detail dirrectly
                    if ($scope.model.jobAssignment.bay) {
                        if ($scope.model.jobAssignment.jobCard) {
                            bayAssignmentFactory.insertDetail(
                                    detailJSON,
                                    function (data) {
                                        var vehId = $scope.getJobCard(data.jobCard).vehicle;
                                        Notification.success($scope.getVehicle(vehId).vehicleNo + ' Vehicle Assigned to ' + $scope.getBay(data.bay).name + ' successfully');

                                        for (var i = 0; i < $scope.model.jobList.length; i++) {
                                            if ($scope.model.jobList[i].indexNo === data.jobCard) {
                                                $scope.model.jobList[i].bay = data.bay;
                                                $scope.model.jobList[i].colourClass = 'default';
                                                $scope.getBayInTime($scope.model.jobList[i]);
                                                $scope.ui.flatRateDefault();
                                            }
                                        }

                                        $scope.model.selectJob = null;
                                        $scope.model.selectBay = null;
                                    }
                            );
                        } else {
                            Notification.error('Select a Bay to Transfer');
                        }
                    } else {
                        Notification.error('Select a Vehicle to Transfer');
                    }
                };
                $scope.ui.flatRateDefault = function () {
                    angular.forEach($scope.model.bayList, function (bay) {
                        bay.flatRate = '00:00:00';
                    });
                    angular.forEach($scope.model.jobList, function (job) {
                        bayAssignmentFactory.getBayInTime(
                                job.indexNo,
                                job.bay,
                                function (data) {
                                    var bay = $scope.getBay(job.bay);
                                    bay.flatRate = '00:00:00';
                                    bay.flatRate = data.outTime;
                                });
                    });
                };
                $scope.ui.setStopTime = function () {
                    var bay = $scope.getBay($scope.model.selectJob.bay);
                    if (bay.assignEmployee) {

                        ConfirmPane.warningConfirm("Do you want to stop  ?")
                                .confirm(function () {
                                    bayAssignmentFactory.setStopTime(
                                            JSON.stringify($scope.model.selectJob),
                                            function (data) {
                                                Notification.success("time stop success !");
                                                // color change
                                                angular.forEach($scope.model.jobList, function (job) {
                                                    if (job.indexNo === data.jobCard) {
                                                        job.colourClass = 'stoped';
                                                        $scope.getBayInTime(job);
                                                        return;
                                                    }
                                                });
                                                $scope.model.selectJob = null;
                                                $scope.model.selectBay = null;
                                            });
                                });
                    } else {
                        Notification.error("Can't time stop from this bay !");

                    }
                };

                $scope.getStopIsStop = function (job) {
                    bayAssignmentFactory.getJobIsStop(
                            job.indexNo,
                            function (data) {
                                job.colourClass = 'default';
                                if (data) {
                                    job.colourClass = 'stoped';
                                }
                                $scope.model.jobList.push(job);
                            });
                };
                $scope.floorTime = function (milisecondsDiff) {
                    var time = Math.floor(milisecondsDiff / (1000 * 60 * 60)).toLocaleString(undefined, {minimumIntegerDigits: 2}) +
                            ":" + (Math.floor(milisecondsDiff / (1000 * 60)) % 60).toLocaleString(undefined, {minimumIntegerDigits: 2}) +
                            ":" + (Math.floor(milisecondsDiff / 1000) % 60).toLocaleString(undefined, {minimumIntegerDigits: 2});
                    return time;
                };
                $scope.getBayInTime = function (job) {
                    bayAssignmentFactory.getBayInTime(
                            job.indexNo,
                            job.bay,
                            function (data) {
                                console.log('$scope.nowTime');
                                console.log($scope.nowTime);
                                console.log('data.inTime');
                                console.log(data.inTime);
                                var startDate = new Date(data.inTime);
                                var endDate = new Date($scope.nowTime);
                                var milisecondsDiff = endDate - startDate;
                                var time = $scope.floorTime(milisecondsDiff);

                                job.flatRate = data.outTime;
                                var bay = $scope.getBay(job.bay);
                                bay.flatRate = '00:00:00';
                                bay.flatRate = job.flatRate;
                                job.processTime = time;
                                job.bayInTime = data.inTime;
                                $scope.setTimePassed(job, time);
                            });
                };
                $scope.setTimePassed = function (job, time) {
                    if (job.colourClass !== 'stoped') {
                        job.colourClass = 'default';
                        if (job.flatRate !== '00:00:00') {
                            if (job.flatRate < time) {
                                job.colourClass = 'passed';
                            }
                        }
                    }
                };
                $scope.ui.addClass = function (job) {

                    if (job.colourClass === 'default') {
                        return '';
                    }
                    if (job.colourClass === 'stoped') {
                        return 'time-stoped';
                    }
                    if (job.colourClass === 'passed') {
                        return 'time-passed';
                    }
                    return 'default';

                };
                $scope.timeCalculation = function () {
                    $timeout(function () {
                        $scope.getNow();
                        angular.forEach($scope.model.jobList, function (job) {
                            var startDate = new Date(job.bayInTime);
                            var milisecondsDiff = new Date($scope.nowTime) - startDate;
                            var time = $scope.floorTime(milisecondsDiff);
                            $scope.setTimePassed(job, time);
                            job.processTime = time;
                        });
                        $scope.timeCalculation();
                    }, 1000);
                };

                $scope.item = function (itemId) {
                    var label = "";
                    angular.forEach($scope.model.itemList, function (item) {
                        if (parseInt(itemId) === parseInt(item.indexNo)) {
                            label = item.indexNo + " - " + item.name;
                            return;
                        }
                    });
                    return label;
                };
                $scope.getNow = function () {
                    bayAssignmentFactory.getServerTime(function (data) {
                        $scope.nowTime = data.outTime;
                    });
                };
                $scope.ui.init = function () {
                    bayAssignmentFactory.loadBays(function (data) {
                        $scope.model.bayList = data;
                    });

                    bayAssignmentFactory.loadJobs(function (data) {
                        $scope.model.jobList = [];

                        angular.forEach(data, function (job) {
                            $scope.getStopIsStop(job);
                            $scope.getBayInTime(job);
                        });

                    });
                    $scope.getNow();

                    bayAssignmentFactory.loadVehicles(function (data) {
                        $scope.model.vehicles = data;
                    });
                    bayAssignmentFactory.loadVehicleTypes(function (data) {
                        $scope.model.vehicleTypes = data;
                    });
                    bayAssignmentFactory.getItems(function (data) {
                        $scope.model.itemList = data;
                    });

                    $scope.timeCalculation();
                };

                $scope.reload2 = function () {
                    $timeout(function () {
                        $scope.model.refershTime -= 1;
                        if ($scope.model.refershTime === 0) {
                            $timeout.cancel(refreshTime);
                            $scope.model.refershTime = 60;

                            bayAssignmentFactory.loadJobs(function (data) {
                                $scope.model.jobList = [];

                                angular.forEach(data, function (job) {
                                    $scope.getStopIsStop(job);
                                    $scope.getBayInTime(job);
                                });

                            });


                            bayAssignmentFactory.loadVehicles(function (data) {
                                $scope.model.vehicles = data;
                            });
                            bayAssignmentFactory.getServerTime(function (data) {
                                $scope.nowTime = data.outTime;
                            });

                        }
                        var refreshTime = $timeout($scope.reload2, 1000);
                    });
                };
                $scope.reload2();
                $scope.ui.init();

            });
}());
