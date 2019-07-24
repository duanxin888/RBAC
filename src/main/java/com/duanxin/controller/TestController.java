package com.duanxin.controller;

import com.duanxin.commons.ApplicationContextHelper;
import com.duanxin.commons.JsonData;
import com.duanxin.dao.SysAclModuleMapper;
import com.duanxin.exceptions.ParamException;
import com.duanxin.model.SysAclModule;
import com.duanxin.params.TestParam;
import com.duanxin.utils.BeanValidator;
import com.duanxin.utils.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName TestController
 * @Description TODO
 * @date 2019/7/16 16:02
 */
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {
    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello(){

        log.info("hello");
        throw new ParamException("test exception");
//        return JsonData.success("hello RBAC");
    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestParam testParam) throws ParamException {
        log.info("validate");
        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule module = moduleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.obj2String(module));

        BeanValidator.check(testParam);
        return JsonData.success("test validate");
    }
}
