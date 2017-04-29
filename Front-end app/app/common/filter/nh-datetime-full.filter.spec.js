describe('nhDatetimeFullFilter', function () {

    beforeEach(angular.mock.module('app'));

    var nhDatetimeFullFilter, dateFilter, translateFilter;
    beforeEach(inject(function ($filter) {
        nhDatetimeFullFilter = $filter("nhDatetimeFullFilter");
        dateFilter = $filter("date");
        translateFilter = $filter("translate");
    }));

    it('should return today date in specified format', function () {
        var todayDate = new Date();
        var filteredDateTime = nhDatetimeFullFilter(todayDate.toString());
        expect(filteredDateTime).toEqual(translateFilter("TODAY") + ' ' + dateFilter(todayDate, "HH:mm"));
    });

    it('should return yesterday date in specified format', function () {
        var yesterdayDate = new Date();
        yesterdayDate.setDate(yesterdayDate.getDate() - 1);
        var filteredDateTime = nhDatetimeFullFilter(yesterdayDate.toString());
        expect(filteredDateTime).toEqual(translateFilter("YESTERDAY") + ' ' + dateFilter(yesterdayDate, "HH:mm"));
    });

    it('should return simple date in specified format', function () {
        var simpleDate = new Date();
        simpleDate.setDate(simpleDate.getDate() - 20);
        var filteredDateTime = nhDatetimeFullFilter(simpleDate.toString());
        expect(filteredDateTime).toEqual(dateFilter(simpleDate, "dd/MM/yyyy") + ' ' + dateFilter(simpleDate, "HH:mm"));
    });
});