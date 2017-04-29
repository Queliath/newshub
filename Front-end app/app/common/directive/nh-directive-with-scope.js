export default function nhDirectiveWithScope() {
    return {
        scope: {
            twoWayBinding: '=',
            oneWayBinding: '@',
            callbackBinding: '&'
        }
    }
}