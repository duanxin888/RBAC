package com.duanxin;

import com.duanxin.beans.PageQuery;
import com.duanxin.dao.SysUserMapper;
import com.duanxin.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SysUserServiceTest
 * @Description TODO
 * @date 2019/7/20 8:03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SysUserServiceTest {

//    @Resource
//    private SysUserService sysUserService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Test
    public void test(){
        System.out.println(sysUserMapper.countByEmail("admin@163.com"));
    }
}
