package com.hongchao.enkore.common;

// Based on the ThredLocal encapsulation tool class
// the user saves and obtains the current login user ID
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    // set value
    public static void setCurrentId(Long id){
        threadLocal.set(id);

    }

    // get value
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
