package com.hongchao.enkore.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

import org.springframework.lang.NonNull;

@Data
public class R<T>
{

    private Integer code; //Code: 1 success, 0 or else failure

    private String msg; //error message

    private T data; //data

    private Map<String, Object> map = new HashMap<>(); //dynamic data

    public static <T> R<T> success(T object)
    {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(@NonNull String msg)
    {
        R<T> r = new R<>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value)
    {
        this.map.put(key, value);
        return this;
    }

}
