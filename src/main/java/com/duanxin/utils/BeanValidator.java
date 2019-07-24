package com.duanxin.utils;

import com.duanxin.exceptions.ParamException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName BeanValidator
 * @Description 校验工具类
 * @date 2019/7/17 10:43
 */
public class BeanValidator {

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    /**
     * @description 基本的校验方法
     * @param  t
     * @param groups
     * @date 2019/7/17 10:49
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    public static <T>Map<String, String> validate(T t, Class... groups){
        // 获取Validator对象
        Validator validator = validatorFactory.getValidator();
        // 获取基本的校验结果
        Set<ConstraintViolation<T>> validateResult = validator.validate(t, groups);
        // 进行判断
        if (validateResult.isEmpty()) {
            return Collections.emptyMap();
        } else {
            LinkedHashMap<String, String> errors = Maps.newLinkedHashMap();
            // 遍历validateResult
            for(Iterator it = validateResult.iterator(); it.hasNext();) {
                ConstraintViolation violation = (ConstraintViolation) it.next();
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }

            return errors;
        }
    }

    /**
     * @description 对list集合的校验
     * @param collection
     * @date 2019/7/17 11:01
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    public static Map<String, String> validateList(Collection<?> collection){
        // 校验集合是否为null
        Preconditions.checkNotNull(collection);
        // 遍历集合
        Iterator<?> it = collection.iterator();
        Map<String , String> errors;

        do {
            if (!it.hasNext()) {
                return Collections.emptyMap();
            }
            Object object = it.next();
            errors = validate(object, new Class[0]);
        } while (errors.isEmpty());

        return errors;
    }

    /**
     * @description 校验包装方法
     * @param [obj, args]
     * @date 2019/7/17 11:30
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    public static Map<String, String> validateObject(Object obj, Object... args) {
        if (args != null && args.length > 0) {
            return validateList(Lists.asList(obj, args));
        } else {
            return validate(obj, new Class[0]);
        }
    }

    public static void check(Object param) throws ParamException {
        Map<String, String> map = BeanValidator.validateObject(param);

        if (MapUtils.isNotEmpty(map)) {
            throw new ParamException(map.toString());
        }
    }
}
