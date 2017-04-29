export default function nhDatetimeShortFilter($filter) {
    return function (creationTime) {
        var creationTimeJs = new Date(creationTime);
        return $filter("date")(creationTimeJs, "HH:mm");
    }
}
