package com.duanxin.commons;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName JsonData
 * @Description 处理json数据
 * @date 2019/7/17 7:36
 */
@Setter
@Getter
public class JsonData implements Serializable {

    private boolean ret;

    private String msg;

    private Object data;

    public JsonData(boolean ret){
        this.ret = ret;
    }

    /**
     * @description 当结果为true时，获取实体类（数据，异常信息）
     * @date 2019/7/17 7:57
     * @return com.duanxin.commons.JsonData
     **/
    public static JsonData success(Object object, String msg){
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        jsonData.msg = msg;
        return jsonData;
    }

    /**
     * @description 当结果为true时，获取实体类（数据）
     * @date 2019/7/17 7:57
     * @return com.duanxin.commons.JsonData
     **/
    public static JsonData success(Object object){
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        return jsonData;
    }

    /**
     * @description 当结果为true时，获取实体类
     * @date 2019/7/17 7:56
     * @return com.duanxin.commons.JsonData
     **/
    public static JsonData success(){
        return new JsonData(true);
    }

    /**
     * @description 当结果为false时，获取实体类（异常信息）
     * @date 2019/7/17 7:58
     * @return com.duanxin.commons.JsonData
     **/
    public static JsonData fail(String msg){
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        return jsonData;
    }

    /**
     * @description 存储数据（返回结果，json数据，异常信息）
     * @date 2019/7/17 8:08
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("ret", ret);
        map.put("data", data);
        map.put("msg", msg);

        return map;
    }
}
