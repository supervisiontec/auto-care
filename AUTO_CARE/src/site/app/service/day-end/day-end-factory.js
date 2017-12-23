(function () {
    angular.module("appModule")
            .factory("dayEndFactory", function () {
                var factory = {};

                factory.data = function () {
                    var data = {
                        "indexNo": null,
                        "number": null,
                        "branch": null,
                        "date": null,
                        "transaction": null,
                        "priceCategory": null,
                        "inTime": null,
                        "outTime": null,
                        "inMileage": null,
                        "nextMileage": null,
                        "status": null,
                        "bay": null,
                        "client": null,
                        "vehicle": null,
                        "serviceChagers": false,
                        "vehicleImages": false,
                        "finalCheck": false,
                        "attenctions": false,
                        "invoice": false,
                        "defaultFinalCheck": false,
                        "rate": "12",
                        "rateReason": null

                    };
                    return data;
                };

                return factory;
            });
}());


