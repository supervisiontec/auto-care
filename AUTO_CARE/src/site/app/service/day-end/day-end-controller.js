(function () {
//module
    angular.module("dayEndModule", ['ui.bootstrap']);
    //controller
    angular.module("dayEndModule")
            .controller("dayEndController", function ($scope, $uibModal, $timeout, $filter, $uibModalStack, dayEndModel, ConfirmPane, Notification) {
                $scope.model = new dayEndModel();
                $scope.ui = {};

                $scope.ui.mode = 'IDEAL';


                //focus
                $scope.ui.loadNumberMap = function () {
                    if ($scope.model.data.date !== null) {
                        var date = $filter('date')($scope.model.data.date, 'yyyy-MM-dd');
                        $scope.model.loadNumberMap(date);
                    }
                };
                $scope.ui.new = function () {
                    //new function
                    $scope.ui.mode = 'NEW';
                    $scope.model.data.date = new Date();
                    
                    //load data to new date
                    var date = $filter('date')($scope.model.data.date, 'yyyy-MM-dd');
                    $scope.model.loadNumberMap(date);


                };
                $scope.ui.save = function () {
                    var date = $filter('date')($scope.model.data.date, 'yyyy-MM-dd');
                    $scope.ui.mode = 'IDEAL';
                    $scope.model.save(date);

                };


            });
}());


