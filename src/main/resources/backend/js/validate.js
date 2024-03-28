function isValidUsername(str) {
    return ['admin', 'editor'].indexOf(str.trim()) >= 0;
}

function isExternal(path) {
    return /^(https?:|mailto:|tel:)/.test(path);
}

function isCellPhone(val) {
    if (!/^\d{3}\d{3}\d{4}$/.test(val)) {
        return false
    } else {
        return true
    }
}

//Verify account
function checkUserName(rule, value, callback) {
    if (value == "") {
        callback(new Error("Please enter your account number"))
    } else if (value.length > 20 || value.length < 3) {
        callback(new Error("Account length should be 3-20"))
    } else {
        callback()
    }
}

//Verify name
function checkName(rule, value, callback) {
    if (value == "") {
        callback(new Error("Please enter your name"))
    } else if (value.length > 50) {
        callback(new Error("Name length should be 1-50"))
    } else {
        callback()
    }
}

function checkPhone(rule, value, callback) {
    // let phoneReg = /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/;
    if (value == "") {
        callback(new Error("Please enter your mobile phone number"))
    } else if (!isCellPhone(value)) {//Introduce the method of checking the mobile phone format encapsulated in methods
        callback(new Error("Please enter the correct mobile phone number!"))
    } else {
        callback()
    }
}


function validID(rule, value, callback) {
    // The ID number is 15 or 18 digits. When it is 15 digits, it is all numbers. The first 17 digits of the 18 digits are numbers.
    // The last digit is the check digit, which may be numbers or the character X.
    let reg = /^\d{3}\d{2}\d{4}$/
    if (value == '') {
        callback(new Error('Please enter your Social Security number'))
    } else if (reg.test(value)) {
        callback()
    } else {
        callback(new Error('Social Security number is incorrect'))
    }
}

function checkEmail(rule, value, callback) {

    let reg = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/
    if (value == '') {
        callback(new Error('Please enter your email'))
    } else if (reg.test(value)) {
        callback()
    } else {
        callback(new Error('email is incorrect'))
    }

}

function checkAddress(rule, value, callback) {

    if (value == '') {
        callback(new Error('Please enter your address'))
    } else if (value.length > 50) {
        callback(new Error("Address should be less than 50"))
    }else{
        callback();
    }
}