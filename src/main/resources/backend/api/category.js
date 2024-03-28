// Query list interface
const getCategoryPage = (params) => {
    return $axios({
        url: '/category/page',
        method: 'get',
        params
    })
}

// Edit page reverse lookup details interface
const queryCategoryById = (id) => {
    return $axios({
        url: `/category/${id}`,
        method: 'get'
    })
}

// Delete current column interface
const deleCategory = (id) => {
    return $axios({
        url: '/category',
        method: 'delete',
        params: {id}
    })
}

// Modify interface
const editCategory = (params) => {
    return $axios({
        url: '/category',
        method: 'put',
        data: {...params}
    })
}

// Add interface.
const addCategory = (params) => {
    return $axios({
        url: '/category',
        method: 'post',
        data: {...params}
    })
}