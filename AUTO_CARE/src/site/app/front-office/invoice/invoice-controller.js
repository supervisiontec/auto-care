(function () {
    angular.module("invoiceModule", ['ui.bootstrap']);
    angular.module("invoiceModule")
            .controller("invoiceController", function ($scope, $sce, invoiceService, systemConfig, $window, $uibModal, $uibModalStack, $filter, optionPane, invoiceModel, Notification, ConfirmPane) {

                $scope.invoiceModel = new invoiceModel();
                $scope.model = {};
                $scope.ui = {};

                $scope.model.currentReportGroup = {};
                $scope.model.currentReport = {};
                $scope.model.currentReport.parameterValues = {};


                $scope.ui.new = function () {
                    $scope.ui.clear();
                    $scope.ui.mode = 'NEW';
                    $scope.invoiceModel.invoiceData.date = $filter('date')(new Date(), 'yyyy-MM-dd');
                };

                //variables pass data to methods
                $scope.selectedJobCardIndexNo = null;
                $scope.selectedCustomer = null;
                $scope.selectedVehicle = null;
                $scope.selectJobCardServiceChagers = null;
                $scope.employeeResponsibiltySelect = true;
                $scope.clientLableView = false;
                $scope.vehicleLableView = false;

                $scope.ui.selectedJobCardRow = function (jobCard) {

                    $scope.invoiceModel.clear();
                    //job card seletion
                    $scope.selectedJobCardIndexNo = jobCard.indexNo;

                    $scope.invoiceModel.invoiceData.jobCard = jobCard.indexNo;
                    $scope.invoiceModel.getJobItemHistory(jobCard.indexNo);

                    $scope.invoiceModel.getClientOverPayment(jobCard.client);
                    $scope.invoiceModel.getClientBalance(jobCard.client);

                    $scope.selectJobCardServiceChagers = jobCard.serviceChagers;

                    $scope.selectedCustomer = jobCard.client;
                    $scope.selectedVehicle = jobCard.vehicle;

                    $scope.invoiceModel.getClientIsNew(jobCard.client);
                    $scope.invoiceModel.getVehicleIsNew(jobCard.vehicle);

                };

                $scope.ui.clear = function () {
                    $scope.selectedJobCardIndexNo = null;
                    $scope.selectJobCardServiceChagers = null;
                    $scope.invoiceModel.clear();
                    $scope.invoiceModel.cashPayment = 0.0;
                    $scope.invoiceModel.settlementAmount = 0.0;
                };

                $scope.ui.load = function (e) {
                    var code = e ? e.keyCode || e.which : 13;
                    if (code === 13) {
                        $scope.invoiceModel.loadInvoiceData()
                                .then(function () {
                                    $scope.ui.mode = 'SELECT';
                                });
                    }
                };
                $scope.ui.registerNewCustomerDetail = function () {
                    //model open
                    $scope.invoiceModel.selectedCustomerObject($scope.selectedCustomer);
                    $uibModal.open({
                        animation: true,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: 'customer.html',
                        scope: $scope,
                        size: 'lg'
                    });

                };
                $scope.ui.registerCustomer = function () {
                    var check = true;
                    if (!$scope.invoiceModel.popupCustomer.name) {
                        check = false;
                        Notification.error('Customer Name is Empty !');
                    }
                    if (!$scope.invoiceModel.popupCustomer.resident) {
                        check = false;
                        Notification.error('select a resident to save !');
                    }
                    if (!$scope.invoiceModel.popupCustomer.mobile) {
                        check = false;
                        Notification.error('mobile number is empty !');
                    }
                    if (!$scope.invoiceModel.popupCustomer.nic) {
                        check = false;
                        Notification.error('NIC is empty !');
                    }
                    if (!$scope.invoiceModel.popupCustomer.customerType) {
                        check = false;
                        Notification.error('customerType is empty !');
                    }
                    if (check) {
                        $scope.invoiceModel.registerCustomer()
                                .then(function (data) {
                                    Notification.success(data.resident+" "+data.name+" registration successfully");
                                    $scope.ui.modelCancel();
                                });
                    }
                };
                $scope.ui.registerNewVehicleDetail = function () {
                    //model open
                    $scope.invoiceModel.selectedVehicleObject($scope.selectedVehicle);
                    $uibModal.open({
                        animation: true,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: 'vehicle.html',
                        scope: $scope,
                        size: 'lg'
                    });

                };
                $scope.ui.registerVehicle=function (){
                     var check = true;
                    if (!$scope.invoiceModel.popupVehicle.client) {
                        check = false;
                        Notification.error('Customer is Empty !');
                    }
                    if (!$scope.invoiceModel.popupVehicle.vehicleType) {
                        check = false;
                        Notification.error('vehicleType is Empty !');
                    }
                    if (!$scope.invoiceModel.popupVehicle.type) {
                        check = false;
                        Notification.error('Type is Empty !');
                    }
                    if (!$scope.invoiceModel.popupVehicle.brand) {
                        check = false;
                        Notification.error('brand is Empty !');
                    }
                    if (!$scope.invoiceModel.popupVehicle.vehicleNo) {
                        check = false;
                        Notification.error('vehicleNo is Empty !');
                    }
                    if (!$scope.invoiceModel.popupVehicle.model) {
                        check = false;
                        Notification.error('model is Empty !');
                    }
                    if (!$scope.invoiceModel.popupVehicle.priceCategory) {
                        check = false;
                        Notification.error('priceCategory is Empty !');
                    }
                    if (check) {
                        $scope.invoiceModel.registerVehicle()
                                .then(function (data) {
                                    Notification.success(data.vehicleNo+" registration successfully");
                                    $scope.ui.modelCancel();
                                });
                    }
                };

                $scope.ui.getRepEmployeeData = function (indexNo) {
                    $scope.invoiceModel.employeeData = $scope.invoiceModel.employee(indexNo);
                };

                $scope.ui.modalOpen = function (indexNo) {

//---------------------------------- invoice ----------------------------------
                    var reportName = "Invoice";
                    //get report details
                    invoiceService.reportData(reportName)
                            .success(function (data) {
                                $scope.model.currentReport.report = data;

                                //get report paramiters
                                invoiceService.listParameters(data)
                                        .success(function (data) {
                                            $scope.model.currentReport.parameters = data;
                                        });

                                //set paramiters values
                                $scope.model.currentReport.parameterValues.INVOICE_NO = indexNo;

                                //view reports
                                invoiceService.viewReport(
                                        $scope.model.currentReport.report,
                                        $scope.model.currentReport.parameters,
                                        $scope.model.currentReport.parameterValues
                                        )
                                        .success(function (response) {
                                            var file = new Blob([response], {type: 'application/pdf'});
                                            var fileURL = URL.createObjectURL(file);

                                            $scope.content = $sce.trustAsResourceUrl(fileURL);

                                            $uibModal.open({
                                                animation: true,
                                                ariaLabelledBy: 'modal-title',
                                                ariaDescribedBy: 'modal-body',
                                                templateUrl: 'invoice_popup.html',
                                                scope: $scope,
                                                size: 'lg'
                                            });

                                        });
                            });
//---------------------------------- end invoice ----------------------------------
                };

                $scope.ui.invoiceViewer = function () {
                    console.log("Invoice_From_Job_Card");
                    console.log("Invoice_From_Job_Card");
                    var reportName = "Invoice_From_Job_Card";
                    //get report details
                    invoiceService.reportData(reportName)
                            .success(function (data) {
                                $scope.model.currentReport.report = data;

                                //get report paramiters
                                invoiceService.listParameters(data)
                                        .success(function (data) {
                                            $scope.model.currentReport.parameters = data;
                                        });

                                //set paramiters values
                                $scope.model.currentReport.parameterValues.JOB_CARD = $scope.selectedJobCardIndexNo;

                                //view reports
                                invoiceService.viewReport(
                                        $scope.model.currentReport.report,
                                        $scope.model.currentReport.parameters,
                                        $scope.model.currentReport.parameterValues
                                        )
                                        .success(function (response) {
                                            var file = new Blob([response], {type: 'application/pdf'});
                                            var fileURL = URL.createObjectURL(file);

                                            $scope.content = $sce.trustAsResourceUrl(fileURL);

                                            $uibModal.open({
                                                animation: true,
                                                ariaLabelledBy: 'modal-title',
                                                ariaDescribedBy: 'modal-body',
                                                templateUrl: 'invoice_popup.html',
                                                scope: $scope,
                                                size: 'lg'
                                            });

                                        });
                            });
                };

                $scope.ui.saveInvoice = function () {
                    if ($scope.selectedJobCardIndexNo) {
                        if (!$scope.invoiceModel.vehicleIsNew) {
                            if (!$scope.invoiceModel.clientIsNew) {
                                if ($scope.invoiceModel.paymentData.chequeAmount > 0 || $scope.invoiceModel.paymentData.balance > 0) {
                                    if (!$scope.invoiceModel.paymentData.respEmployee) {
                                        optionPane.dangerMessage("plase select reponsibilty employee");
                                        $scope.employeeResponsibiltySelect = false;
                                    } else {
                                        ConfirmPane.successConfirm("Do you want to save invoice")
                                                .confirm(function () {
                                                    $scope.invoiceModel.saveInvoice()
                                                            .then(function (data) {
                                                                $scope.ui.mode = "IDEAL";
                                                                $scope.ui.clear();
                                                                ConfirmPane.successConfirm("Do You Want To Print Invoice")
                                                                        .confirm(function () {
                                                                            $scope.ui.modalOpen(data.indexNo);
                                                                        });
                                                            });
                                                });
                                    }
                                } else {
                                    ConfirmPane.successConfirm("Do you want to save invoice")
                                            .confirm(function () {
                                                $scope.invoiceModel.saveInvoice()
                                                        .then(function (data) {
                                                            $scope.ui.mode = "IDEAL";
                                                            $scope.ui.clear();
                                                            ConfirmPane.successConfirm("Do You Want To Print Invoice")
                                                                    .confirm(function () {
                                                                        console.log(data);
                                                                        $scope.ui.modalOpen(data.indexNo);
                                                                    });
                                                        });
                                            });
                                }
                            } else {
                                Notification.error('Register New Customer Details to Save Invoice !');
                            }
                        } else {
                            Notification.error('Register New Vehicle Details to Save Invoice !');
                        }
                    } else {
                        Notification.error("select vehicle");
                    }
                };

                $scope.ui.getCashPayment = function (amount, type) {
                    if ($scope.selectedJobCardIndexNo) {
                        if (0.0 === parseFloat($scope.invoiceModel.getTotalPaymentTypeWise('CASH'))) {
                            $scope.invoiceModel.getInsertCashPayment(amount, type);
                        }
                    } else {
                        Notification.error("please select vehicle");
                    }
                };

                $scope.ui.getCardAndChequePaymentDelete = function (number) {
                    $scope.invoiceModel.getCardAndChequePaymentDelete(number);
                };

                $scope.ui.getCashPaymentDelete = function () {
                    $scope.invoiceModel.cashPayment = 0.0;
                    $scope.invoiceModel.getCashPaymentDelete();
                };

                $scope.ui.getInsertCardAndChequePayment = function (paymentInformation, type) {
                    if ($scope.selectedJobCardIndexNo) {
                        if (type === "CHEQUE") {
                            if (!paymentInformation.number) {
                                Notification.error("please enter cheque no");
                            } else if (!paymentInformation.chequeDate) {
                                Notification.error("please enter cheque date");
                            } else if (!paymentInformation.bank) {
                                Notification.error("please enter cheque bank");
                            } else if (!paymentInformation.bankBranch) {
                                Notification.error("please enter cheque branch");
                            } else if (!paymentInformation.amount) {
                                Notification.error("please enter cheque amount");
                            } else {
                                $scope.invoiceModel.getInsertCardAndChequePayment(paymentInformation, type);
                            }
                        } else if (type === "CARD") {
                            if (!paymentInformation.cardType) {
                                Notification.error("please enter card type");
                            } else if (!paymentInformation.amount) {
                                Notification.error("please enter card amount");
                            } else {
                                $scope.invoiceModel.getInsertCardAndChequePayment(paymentInformation, type);
                            }
                        }

                    } else {
                        Notification.error("please select vehicle");
                    }
                };

                $scope.ui.insertClientOverPaymentSettlment = function (overPayment, amount) {
                    if ($scope.selectedJobCardIndexNo) {
                        if (0.0 === parseFloat($scope.invoiceModel.getTotalPaymentTypeWise('OVER_PAYMENT_SETTLEMENT'))) {
                            if (overPayment >= amount) {
                                $scope.invoiceModel.insertClientOverPaymentSettlment(amount, 'OVER_PAYMENT_SETTLEMENT');
                            } else {
                                Notification.error("plase enter valid amount");
                            }
                        }
                    } else {
                        Notification.error("select vehicle");
                    }
                };

                $scope.ui.deleteOverPayment = function () {
                    $scope.invoiceModel.settlementAmount = 0.0;
                    $scope.invoiceModel.deleteOverPayment();
                };

                $scope.ui.getDiscountRate = function () {
                    $scope.invoiceModel.invoiceData.discountRate = parseFloat(($scope.invoiceModel.invoiceData.discountAmount * 100) / $scope.invoiceModel.invoiceData.amount);
                    $scope.invoiceModel.invoiceData.netAmount = parseFloat($scope.invoiceModel.invoiceData.amount - $scope.invoiceModel.invoiceData.discountAmount);
                };

                $scope.ui.getDiscountAmount = function () {
                    $scope.invoiceModel.invoiceData.discountAmount = parseFloat(($scope.invoiceModel.invoiceData.amount * $scope.invoiceModel.invoiceData.discountRate) / 100);
                    $scope.invoiceModel.invoiceData.netAmount = parseFloat($scope.invoiceModel.invoiceData.amount - $scope.invoiceModel.invoiceData.discountAmount);
                };
                $scope.ui.modelCancel = function () {
                    $uibModalStack.dismissAll();
                };
                $scope.ui.registerCusromer = function () {
                    Notification.success('reg');
                };

                $scope.init = function () {
                    $scope.$watch("[invoiceModel.invoiceData.netAmount,invoiceModel.paymentInformationList.length]", function (newVal, oldVal) {
                        $scope.invoiceModel.getPaymentDetails();
                    }, true);
                    $scope.ui.mode = 'IDEAL';
                };

                $scope.init();
            });
}());