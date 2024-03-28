// get dish category list
function categoryListApi() {
    return $axios({
        'url': '/category/list',
        'method': 'get',
    })
}

// get dish
function dishListApi(data) {
    return $axios({
        'url': '/dish/list',
        'method': 'get',
        params: {...data}
    })
}

// get meal category
function setmealListApi(data) {
    return $axios({
        'url': '/setmeal/list',
        'method': 'get',
        params: {...data}
    })
}

// get shopping cart list
function cartListApi(data) {
    return $axios({
        'url': '/shoppingCart/list',
        //'url': '/front/cartData.json',
        'method': 'get',
        params: {...data}
    })
}

// add to shopping cart
function addCartApi(data) {
    return $axios({
        'url': '/shoppingCart/add',
        'method': 'post',
        data
    })
}

// edit shopping cart
function updateCartApi(data) {
    return $axios({
        'url': '/shoppingCart/sub',
        'method': 'post',
        data
    })
}

// delete shopping cart
function clearCartApi() {
    return $axios({
        'url': '/shoppingCart/clean',
        'method': 'delete',
    })
}

// get dish form meal
function setMealDishDetailsApi(id) {
    return $axios({
        'url': `/setmeal/dish/${id}`,
        'method': 'get',
    })
}


