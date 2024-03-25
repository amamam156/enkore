// Query list data
const getSetmealPage = (params) => {
    return $axios({
        url: '/setmeal/page',
        method: 'get',
        params
    })
}

// Delete data interface
const deleteSetmeal = (ids) => {
    return $axios({
        url: '/setmeal',
        method: 'delete',
        params: {ids}
    })
}

// Modify data interface
const editSetmeal = (params) => {
    return $axios({
        url: '/setmeal',
        method: 'put',
        data: {...params}
    })
}

// Add data interface
const addSetmeal = (params) => {
    return $axios({
        url: '/setmeal',
        method: 'post',
        data: {...params}
    })
}

// Query details interface
const querySetmealById = (id) => {
    return $axios({
        url: `/setmeal/${id}`,
        method: 'get'
    })
}

// Bulk sales are prohibited
const setmealStatusByStatus = (params) => {
    return $axios({
        url: `/setmeal/status/${params.status}`,
        method: 'post',
        params: {ids: params.ids}
    })
}
