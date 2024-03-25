// Query list interface
const getDishPage = (params) => {
    return $axios({
        url: '/dish/page',
        method: 'get',
        params
    })
}

// Delete interface
const deleteDish = (ids) => {
    return $axios({
        url: '/dish',
        method: 'delete',
        params: {ids}
    })
}

// Modify interface
const editDish = (params) => {
    return $axios({
        url: '/dish',
        method: 'put',
        data: {...params}
    })
}

// Add interface
const addDish = (params) => {
    return $axios({
        url: '/dish',
        method: 'post',
        data: {...params}
    })
}

// Query details
const queryDishById = (id) => {
    return $axios({
        url: `/dish/${id}`,
        method: 'get'
    })
}

// Get a list of dish categories
const getCategoryList = (params) => {
    return $axios({
        url: '/category/list',
        method: 'get',
        params
    })
}

// Interface for checking menu list
const queryDishList = (params) => {
    return $axios({
        url: '/dish/list',
        method: 'get',
        params
    })
}

// File down preview
const commonDownload = (params) => {
    return $axios({
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        url: '/common/download',
        method: 'get',
        params
    })
}

// Sales start and stop---Batch start and stop sales interface
const dishStatusByStatus = (params) => {
    return $axios({
        url: `/dish/status/${params.status}`,
        method: 'post',
        params: {ids: params.id}
    })
}