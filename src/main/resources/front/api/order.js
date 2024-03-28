// submit
function addOrderApi(data) {
    return $axios({
        'url': '/order/submit',
        'method': 'post',
        data
    })
}

// order history
function orderListApi() {
    return $axios({
        'url': '/order/list',
        'method': 'get',
    })
}

// page
function orderPagingApi(data) {
    return $axios({
        'url': '/order/userPage',
        'method': 'get',
        params: {...data}
    })
}

// order again
function orderAgainApi(data) {
    return $axios({
        'url': '/order/again',
        'method': 'post',
        data
    })
}