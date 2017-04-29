export default function nhDatetimeFullFilter($filter) {
    function filter(creationTime) {
        var creationTimeJs = new Date(creationTime);

        var dateString, timeString;

        var currentTime = new Date();
        if ((currentTime.getFullYear() == creationTimeJs.getFullYear()) && (currentTime.getMonth() == creationTimeJs.getMonth()) && (currentTime.getDate() - 1 == creationTimeJs.getDate())) {
            dateString = $filter("translate")("YESTERDAY");
        } else if ((currentTime.getFullYear() == creationTimeJs.getFullYear()) && (currentTime.getMonth() == creationTimeJs.getMonth()) && (currentTime.getDate() == creationTimeJs.getDate())) {
            dateString = $filter("translate")("TODAY");
        } else {
            dateString = $filter("date")(creationTimeJs, "dd/MM/yyyy");
        }

        timeString = $filter("date")(creationTimeJs, "HH:mm");

        return dateString + ' ' + timeString;
    }

    filter.$stateful = true;

    return filter;
}
