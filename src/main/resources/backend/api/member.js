// Get member list function
function getMemberList(params) {
    return $axios({
        url: '/employee/page',
        method: 'get',
        params
    })
}

// Modify - Enable or disable employee interface
function enableOrDisableEmployee(params) {
    return $axios({
        url: '/employee',
        method: 'put',
        data: {...params}
    })
}

// Add - Add employee function
function addEmployee(params) {
    return $axios({
        url: '/employee',
        method: 'post',
        data: {...params}
    })
}

// Modify - Edit employee function
function editEmployee(params) {
    return $axios({
        url: '/employee',
        method: 'put',
        data: {...params}
    })
}

// Edit page reverse lookup details interface
function queryEmployeeById(id) {
    return $axios({
        url: `/employee/${id}`,
        method: 'get'
    })
}
