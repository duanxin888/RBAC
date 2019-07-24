package com.duanxin.service;

import com.duanxin.beans.PageQuery;
import com.duanxin.beans.PageResult;
import com.duanxin.commons.RequestHolder;
import com.duanxin.dao.SysUserMapper;
import com.duanxin.exceptions.ParamException;
import com.duanxin.model.SysUser;
import com.duanxin.params.UserParam;
import com.duanxin.utils.BeanValidator;
import com.duanxin.utils.IpUtil;
import com.duanxin.utils.MD5Util;
import com.duanxin.utils.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SysUserService
 * @Description 用户service层类
 * @date 2019/7/19 17:19
 */
@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysLogService sysLogService;

    /**
     * @description 获取所有用户数据
     * @param []
     * @date 2019/7/22 17:12
     * @return java.util.List<com.duanxin.model.SysUser>
     **/
    public List<SysUser> getAll() {
        return sysUserMapper.getAll();
    }

    /**
     * @description 查询分页数据
     * @param [deptId, page]
     * @date 2019/7/20 7:34
     * @return com.duanxin.beans.PageResult<com.duanxin.model.SysUser>
     **/
    public PageResult<SysUser> getPageByDeptId(Integer deptId, PageQuery page) {
        // 进行校验
        BeanValidator.check(page);
        // 总条数
        int count = sysUserMapper.countByDeptId(deptId);
        if(count > 0) {
            // 获取查询数据
            List<SysUser> sysUsers = sysUserMapper.getPageByDeptId(deptId, page);
            return PageResult.<SysUser>builder().total(count).data(sysUsers).build();
        }
        return PageResult.<SysUser>builder().build();
    }

    /**
     * @description 根据keyword获取用户信息
     * @param [keyword] 可以为telephone，email
     * @date 2019/7/19 18:28
     * @return com.duanxin.model.SysUser
     **/
    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }

    /**
     * @description 保存用户信息
     * @param [param]
     * @date 2019/7/19 17:22
     **/
    public void save(UserParam param) {
        // 进行校验
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone())) {
            throw new ParamException("电话已被使用");
        }
        if (checkEmailExist(param.getEmail())) {
            throw new ParamException("邮箱已被使用");
        }

        // 调用工具随机生成
        String password = PasswordUtil.randomPassword();
        // todo
        password = "123456";
        // todo: 加密
        String encryptedPassword = MD5Util.encrypt(password);
        SysUser sysUser = SysUser.builder().username(param.getUsername())
                .telephone(param.getTelephone()).email(param.getEmail())
                .password(encryptedPassword).deptId(param.getDeptId())
                .status(param.getStatus()).remark(param.getRemark()).build();
        sysUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysUser.setOperateTime(new Date());
        sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());

        // todo: send email

        sysUserMapper.insertSelective(sysUser);

        sysLogService.saveUserLog(null, sysUser);
    }

    /**
     * @description 更新用户
     * @param [param]
     * @date 2019/7/19 18:04
     **/
    public void update(UserParam param) {
        // 进行校验
        BeanValidator.check(param);
        // 获取更新前的用户信息
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        if (!StringUtils.equals(before.getEmail(), param.getEmail())) {
            if (checkEmailExist(param.getEmail())) {
                throw new ParamException("邮件已存在");
            }
        }
        if (!StringUtils.equals(before.getTelephone(), param.getTelephone())) {
            if (checkTelephoneExist(param.getTelephone())) {
                throw new ParamException("电话已存在");
            }
        }

        SysUser after = SysUser.builder().id(param.getId())
                .username(param.getUsername()).telephone(param.getTelephone())
                .email(param.getEmail()).deptId(param.getDeptId())
                .status(param.getStatus()).remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        sysUserMapper.updateByPrimaryKeySelective(after);

        sysLogService.saveUserLog(before, after);
    }

    /**
     * @description 校验电话是否被使用
     * @param [telephone, id]
     * @date 2019/7/19 17:28
     * @return boolean
     **/
    private boolean checkTelephoneExist(String telephone) {
        return sysUserMapper.countByTelephone(telephone) > 0;
    }

    /**
     * @description 校验邮箱是否被使用
     * @param [email, id]
     * @date 2019/7/19 17:28
     * @return boolean
     **/
    private boolean checkEmailExist(String email) {
        return sysUserMapper.countByEmail(email) > 0;
    }

}
