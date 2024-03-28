// Get all addresses
function addressListApi() {
    return $axios({
        'url': '/addressBook/list',
        'method': 'get',
    })
}

// Get the latest address
function addressLastUpdateApi() {
    return $axios({
        'url': '/addressBook/lastUpdate',
        'method': 'get',
    })
}

// Add address
function addAddressApi(data) {
    return $axios({
        'url': '/addressBook',
        'method': 'post',
        data
    })
}

// update address
function updateAddressApi(data) {
    return $axios({
        'url': '/addressBook',
        'method': 'put',
        data
    })
}

// delete address
function deleteAddressApi(params) {
    return $axios({
        'url': '/addressBook',
        'method': 'delete',
        params
    })
}

// find single address
function addressFindOneApi(id) {
    return $axios({
        'url': `/addressBook/${id}`,
        'method': 'get',
    })
}

// set default address
function setDefaultAddressApi(data) {
    return $axios({
        'url': '/addressBook/default',
        'method': 'put',
        data
    })
}

// get default address
function getDefaultAddressApi() {
    return $axios({
        'url': `/addressBook/default`,
        'method': 'get',
    })
}