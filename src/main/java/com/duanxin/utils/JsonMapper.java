package com.duanxin.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName JsonMapper
 * @Description 类对象和json进行互换
 * @date 2019/7/17 14:53
 */
@Slf4j
public class JsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // config
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    /**
     * @description 将Object对象转化为String
     * @param [src] object对象
     * @date 2019/7/17 15:11
     * @return java.lang.String
     **/
    public static <T> String obj2String(T src) {
        if (src == null) {
            return null;
        }

        try {
            return src instanceof String ? (String)src : objectMapper.writeValueAsString(src);
        } catch (Exception e) {
            log.warn("object to String is exception,error:{}", e);
            return null;
        }
    }

    public static <T>Object string2Obj(String src, TypeReference<T> tTypeReference){
        if (src == null || tTypeReference == null) {
            return null;
        }

        try {
            return tTypeReference.getType().equals(String.class) ? src : objectMapper.readValue(src, tTypeReference);
        } catch (Exception e) {
            log.warn("String to object is exception, String:{},TypeReference:{},error:{}", src, tTypeReference.getType(), e);
            return null;
        }
    }
}
