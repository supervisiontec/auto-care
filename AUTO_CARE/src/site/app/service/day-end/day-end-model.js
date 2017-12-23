(function () {
    angular.module("appModule")
            .factory("dayEndModel", function (dayEndService, dayEndFactory, $q) {
                function dayEnd() {
                    this.constructor();
                }

                dayEnd.prototype = {
                    data: {},
                    map: [],
                    constructor: function () {
                        var that = this;
                        that.data = dayEndFactory.data();
                    },
                    loadNumberMap: function (date) {
                        var that = this;
                        dayEndService.loadNumberMap(date)
                                .success(function (data) {
                                    that.map = data;
                                    that.data.grossSales = that.map['grossSales'];
                                    that.data.discount = that.map['discount'];
                                    that.data.netSales = parseFloat(that.map['grossSales']) - parseFloat(that.map['discount']);
                                    that.data.cashReceived = that.map['cashReceived'];
                                    that.data.cardReceived = that.map['cardReceived'];
                                    that.data.chequeReceived = that.map['chequeReceived'];
                                    that.data.debitAmount = that.map['debitAmount'];
                                    that.data.otherIncome = that.map['otherIncome'];
                                    that.data.overPayment = that.map['overPayment'] ? that.map['overPayment'] : 0.00;
                                    console.log(that.data.netSales);
                                    console.log(data);
                                });
                    },
                    save: function (date) {
                        var that=this;
                        dayEndService.save(date)
                                .success(function (data) {
                                    console.log(data);
                                });
                    }



                };
                return dayEnd;
            });
}());
