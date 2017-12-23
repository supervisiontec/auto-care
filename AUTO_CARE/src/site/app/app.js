(function () {
    //index module
    angular.module("appModule", [
        "ngRoute",
        "ngCookies",
        "ui.bootstrap",
        //service
        "dailyCheckListModule",
        "serviceSelectionModule",
        "requestItemModule",
        "invoiceModule",
        "itemSalesModule",
        "bayItemIssueModule",
        "finalCheckListModule",
        "dayEndModule",
        "customerSatisfactionModule",
        //pending
        "employeeAssignmentModule",
        "bayAssignmentModule",
        "dashBoardModule",
        //master
        "itemModule",
        "reOrderLevelModule",
        "subCategoryModule",
        "categoryModule",
        "ItemCategoryModule",
        "itemDepartmentModule",
        "brandModule",
        "bayModule",
        "vehicleEntranceModule",
        "priceCategoryModule",
        "vehicleTypeModule",
        "clientModule",
        "vehicleModule",
        "jobCardModule",
        "subItemModule",
        "grnModule",
        "app",
        "stockTransferBranchInModule",
        "stockTransferBranchOutApproveModule",
        "stockTransferBranchOutModule",
        "directGrnModule",
        "grnApproveModule",
        "purchaseOrderRequestModule",
        "purchaseOrderApproveModule",
        "stockTransferInternalOutModule",
        "stockTransferInternalInModule",
        "itemSelectionModule",
        "paymentVoucherModule",
        "jobCardEditModule",
        "vehicleImageManage",
        //appointment
        "appointmentModule",
        "appointmentModuleApprove",
        "fingerPrintModule"
    ]);

    //constants
    angular.module("appModule")
            .constant("systemConfig", {
                apiUrl:
                        location.hostname === 'localhost'
                        ? "http://localhost:8090"
                        : location.protocol + "//" + location.hostname + (location.port ? ":" + location.port : "")
            });

    //route config
    angular.module("appModule")
            .config(function ($routeProvider) {
                $routeProvider
                        //system
                        .when("/", {
                            redirectTo: "/service/vehicle-entrance"
                        })
                        .when("/login", {
                            templateUrl: "app/system/login/login.html",
                            controller: "LoginController"
                        })

                        .when("/reports/general/report-viewer", {
                            templateUrl: "app/reports/report-viewer/report-viewer.html",
                            controller: "ReportViewerController"
                        })

                        .when("/front-office/invoice/invoice_view/:invoiceIndexNo", {
                            templateUrl: "app/front-office/invoice/invoice_view/invoice_view.html",
                            controller: "InvoiceViewController"
                        })

                        //service
                        .when("/service/vehicle-image-manage", {
                            templateUrl: "app/service/vehicle-image-manage/vehicle-image-manage.html",
                            controller: "vehicleImageController"
                        })
                        .when("/service/vehicle-entrance", {
                            templateUrl: "app/service/vehicle-entrance/vehicle-entrance.html",
                            controller: "vehicleEntranceController"
                        })

                        .when("/service/service-selection", {
                            templateUrl: "app/service/service-selection/service-selection.html",
                            controller: "serviceSelectionController"
                        })

                        .when("/service/item-selection", {
                            templateUrl: "app/service/service-selection/item-selection.html",
                            controller: "itemSelectionController"
                        })

                        .when("/service/service-selection/:jobCardIndexNo", {
                            templateUrl: "app/service/service-selection/service-selection.html",
                            controller: "serviceSelectionController"
                        })

                        .when("/service/final-check-list", {
                            templateUrl: "app/service/final-check-list/final-check-list.html",
                            controller: "finalCheckListController"
                        })

                        .when("/service/customer-satisfaction", {
                            templateUrl: "app/service/customer-satisfaction/customer-satisfaction.html",
                            controller: "customerSatisfactionController"
                        })

                        .when("/service/job-card-edit", {
                            templateUrl: "app/service/job-card-edit/job-card-edit.html",
                            controller: "jobCardEditController"
                        })
                        .when("/service/day-end", {
                            templateUrl: "app/service/day-end/day-end.html",
                            controller: "dayEndController"
                        })

                        //front-office
                        .when("/front-office/invoice", {
                            templateUrl: "app/front-office/invoice/invoice.html",
                            controller: "invoiceController"
                        })
                        .when("/front-office/item-sales", {
                            templateUrl: "app/front-office/item-sales/item-sales.html",
                            controller: "itemSalesController"
                        })
                        .when("/front-office/payment-voucher", {
                            templateUrl: "app/front-office/payment-voucher/payment-voucher.html",
                            controller: "paymentVoucherController"
                        })
                        .when("/stock/request-item", {
                            templateUrl: "app/stock/request-item/request-item.html",
                            controller: "requestItemController"
                        })
                        .when("/front-office/bay-item-issue", {
                            templateUrl: "app/front-office/bay-item-issue/bay-item-issue.html",
                            controller: "bayItemIssueController"
                        })

                        //finger-print-manual
                        .when("/front-office/finger-print-manual", {
                            templateUrl: "app/front-office/finger-print-manual/finger-print-manual.html",
                            controller: "fingerPrintController"
                        })

                        //daily-check-list
                        .when("/service/daily-check-list", {
                            templateUrl: "app/service/daily-check-list/daily-check-list.html",
                            controller: "dailyCheckListController"
                        })

                        //employee-assignment
                        .when("/service/employee-assignment", {
                            templateUrl: "app/service/employee-assignment/employee-assignment.html",
                            controller: "employeeAssignmentController"
                        })
                        //bay-assignment
                        .when("/service/bay-assignment", {
                            templateUrl: "app/service/bay-assignment/bay-assignment.html",
                            controller: "bayAssignmentController"
                        })
                        //employee-assignment
                        .when("/service/employee-assignment", {
                            templateUrl: "app/service/employee-assignment/employee-assignment.html",
                            controller: "employeeAssignmentController"
                        })
                        //dash-Board
                        .when("/front-office/service-dashboard", {
                            templateUrl: "app/front-office/service-dashboard/service-dashboard.html",
                            controller: "dashBoardController"
                        })
                        //grn request
                        .when("/front-office/grn/grn-request", {
                            templateUrl: "app/front-office/grn/grn-request/grn-request.html",
                            controller: "grnController"
                        })
                        //grn approve
                        .when("/front-office/grn/grn-approve", {
                            templateUrl: "app/front-office/grn/grn-approve/grn-approve.html",
                            controller: "grnApproveController"
                        })
                        //grn direct
                        .when("/front-office/grn/grn-direct", {
                            templateUrl: "app/front-office/grn/grn-direct/grn.html",
                            controller: "directGrnController"
                        })
                        //purchase order request
                        .when("/front-office/purchase-order/request", {
                            templateUrl: "app/front-office/purchase-order/request/purchase-order-request.html",
                            controller: "purchaseOrderRequestController"
                        })
                        //purchase order approve
                        .when("/front-office/purchase-order/approve", {
                            templateUrl: "app/front-office/purchase-order/approve/purchase-order-approve.html",
                            controller: "purchaseOrderApproveController"
                        })


                        //master
                        .when("/master/item", {
                            templateUrl: "app/master/item/item.html",
                            controller: "itemController"
                        })
                        .when("/master/employee", {
                            templateUrl: "app/master/employee/employee.html",
                            controller: "employeeController"
                        })
                        .when("/master/sub-category", {
                            templateUrl: "app/master/sub-category/sub-category.html",
                            controller: "subCategoryController"
                        })
                        .when("/master/category", {
                            templateUrl: "app/master/category/category.html",
                            controller: "categoryController"
                        })
                        .when("/master/item-category", {
                            templateUrl: "app/master/item-category/item-category.html",
                            controller: "ItemCategoryController"
                        })
                        .when("/master/item-department", {
                            templateUrl: "app/master/item-department/item-department.html",
                            controller: "itemDepartmentController"
                        })
                        .when("/master/brand", {
                            templateUrl: "app/master/brand/brand.html",
                            controller: "brandController"
                        })
                        .when("/master/bay", {
                            templateUrl: "app/master/bay/bay.html",
                            controller: "bayController"
                        })
                        .when("/master/price-category", {
                            templateUrl: "app/master/price-category/price-category.html",
                            controller: "priceCategoryController"
                        })
                        .when("/master/vehicle-type", {
                            templateUrl: "app/master/vehicle-type/vehicle-type.html",
                            controller: "vehicleTypeController"
                        })
                        .when("/master/client", {
                            templateUrl: "app/master/client/client.html",
                            controller: "clientController"
                        })
                        .when("/master/client/:client", {
                            templateUrl: "app/master/client/client.html",
                            controller: "clientController"
                        })
                        .when("/master/vehicle", {
                            templateUrl: "app/master/vehicle/vehicle.html",
                            controller: "vehicleController"
                        })
                        .when("/master/vehicle/:vehicle", {
                            templateUrl: "app/master/vehicle/vehicle.html",
                            controller: "vehicleController"
                        })
                        .when("/front-office/job-card", {
                            templateUrl: "app/front-office/job-card/job-card.html",
                            controller: "jobCardController"
                        })
                        .when("/master/sub-item", {
                            templateUrl: "app/master/sub-item/sub-item.html",
                            controller: "subItemController"
                        })
                        .when("/master/re-order-level", {
                            templateUrl: "app/master/re-order-level/re-order-level.html",
                            controller: "reOrderLevelController"
                        })
                        .when("/master/account", {
                            templateUrl: "app/master/account/account.html"
//                            controller: "reOrderLevelController"
                        })
                        .when("/master/account-settings", {
                            templateUrl: "app/master/account-settings/account-settings.html"
//                            controller: "reOrderLevelController"
                        })

                        //stock transfer
                        //branch-transfer-out
                        .when("/stock/transfer/branch-transfer-out", {
                            templateUrl: "app/front-office/stock/branch-transfer/branch-transfer-out/branch-transfer-out.html",
                            controller: "stockTransferBranchOutController"
                        })
                        //branch-transfer-out-approve
                        .when("/stock/transfer/branch-transfer-out-approve", {
                            templateUrl: "app/front-office/stock/branch-transfer/branch-transfer-out-approve/branch-transfer-out-approve.html",
                            controller: "stockTransferBranchOutApproveController"
                        })
                        //branch-transfer-in
                        .when("/stock/transfer/branch-transfer-in", {
                            templateUrl: "app/front-office/stock/branch-transfer/branch-transfer-in/branch-transfer-in.html",
                            controller: "stockTransferBranchInController"
                        })
                        //internal-transfer-out
                        .when("/stock/transfer/internal-transfer-out", {
                            templateUrl: "app/front-office/stock/internal-transfer/internal-transfer-out/internal-transfer-out.html",
                            controller: "stockTransferInternalOutController"
                        })
                        //internal-transfer-in
                        .when("/stock/transfer/internal-transfer-in", {
                            templateUrl: "app/front-office/stock/internal-transfer/internal-transfer-in/internal-transfer-in.html",
                            controller: "stockTransferInternalInController"
                        })
                        //supplier payment
                        .when("/supplier-payment/supplier-payment", {
                            templateUrl: "app/front-office/supplier-payment/supplier-payment.html"
//                            controller: "stockTransferInternalInController"
                        })
                        //appointment
                        .when("/appointment/create-appointment", {
                            templateUrl: "app/appointment/create-appointment/create-appointment.html",
                            controller: "appointmentCreateController"
                        })
                        .when("/appointment/approve-appointment", {
                            templateUrl: "app/appointment/approve-appointment/approve-appointment.html",
                            controller: "appointmentApproveController"
                        })
                        //accounts
                        .when("/accounts/write-cheque", {
                            templateUrl: "app/accounts/cheque/write-cheque.html"
//                            controller: "chequeController"
                        })
                        .when("/accounts/petty-cash", {
                            templateUrl: "app/accounts/petty-cash/petty-cash.html"
//                            controller: "chequeController"
                        })
                        .when("/accounts/fund-transfer", {
                            templateUrl: "app/accounts/fund-transfer/fund-transfer.html"
//                            controller: "chequeController"
                        })
                        .when("/accounts/deposit", {
                            templateUrl: "app/accounts/deposit/deposit.html"
//                            controller: "chequeController"
                        })
                        .when("/accounts/return-cheque", {
                            templateUrl: "app/accounts/return-cheque/return-cheque.html"
//                            controller: "chequeController"
                        })
                        .when("/accounts/payment-to-diposit", {
                            templateUrl: "app/accounts/payment-to-deposit/payment-to-deposit.html"
//                            controller: "chequeController"
                        })
                        .when("/accounts/journal", {
                            templateUrl: "app/accounts/journal/journal.html"
//                            controller: "chequeController"
                        })
                        .otherwise({
                            redirectTo: "/"
                        });
            });

}());
