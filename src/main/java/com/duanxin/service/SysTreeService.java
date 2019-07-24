package com.duanxin.service;

import com.duanxin.dao.SysAclMapper;
import com.duanxin.dao.SysAclModuleMapper;
import com.duanxin.dao.SysDeptMapper;
import com.duanxin.dao.SysRoleAclMapper;
import com.duanxin.dto.AclDto;
import com.duanxin.dto.AclModuleLevelDto;
import com.duanxin.dto.DeptLevelDto;
import com.duanxin.model.SysAcl;
import com.duanxin.model.SysAclModule;
import com.duanxin.model.SysDept;
import com.duanxin.model.SysUser;
import com.duanxin.utils.LevelUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.acl.Acl;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SysTreeService
 * @Description 获取树结构的业务层类
 * @date 2019/7/18 7:25
 */
@Service
public class SysTreeService {

    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Resource
    private SysCoreService sysCoreService;
    @Resource
    private SysAclMapper sysAclMapper;

    /**
     * @description 获取用户权限树
     * @param [userId]
     * @date 2019/7/23 8:36
     * @return java.util.List<com.duanxin.dto.AclModuleLevelDto>
     **/
    public List<AclModuleLevelDto> userAclTree(Integer userId) {
        // 获取用户权限
        List<SysAcl> userAclList = sysCoreService.getUserAclList(userId);
        List<AclDto> aclDtoList = Lists.newArrayList();
        // 进行权限适配
        for (SysAcl sysAcl : userAclList) {
            AclDto dto = AclDto.adapt(sysAcl);
            dto.setChecked(true);
            dto.setHasAcl(true);
            aclDtoList.add(dto);
        }

        return aclListToTree(aclDtoList);
    }

    /**
     * @description 根据角色id获取权限树
     * @param [roleId]
     * @date 2019/7/22 7:14
     * @return java.util.List<com.duanxin.dto.AclModuleLevelDto>
     **/
    public List<AclModuleLevelDto> roleTree(Integer roleId) {
        // 1，当前用户已分配的权限
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();
        // 2，当前角色已分配的权限
        List<SysAcl> roleAclList = sysCoreService.getRoleAclList(roleId);
        // 3，当前系统所有权限
        List<AclDto> aclDtos = Lists.newArrayList();

        // 获取当前用户权限id集合
        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        // 获取角色权限id集合
        Set<Integer> roleAclIdSet = roleAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());

        // 获取所有权限
        List<SysAcl> sysAcls = sysAclMapper.getAll();

        // 进行遍历适配
        for (SysAcl sysAcl : sysAcls) {
            AclDto dto = AclDto.adapt(sysAcl);
            // 进行判断
            if (userAclIdSet.contains(sysAcl.getId())) {
                // 该用户有操作的权限
                dto.setHasAcl(true);
            }
            if (roleAclIdSet.contains(sysAcl.getId())) {
                // 该角色有被选择的权限
                dto.setChecked(true);
            }
            aclDtos.add(dto);
        }
        return aclListToTree(aclDtos);
    }

    /**
     * @description 将权限集合转化为权限树
     * @param [aclDtos]
     * @date 2019/7/23 8:33
     * @return java.util.List<com.duanxin.dto.AclModuleLevelDto>
     **/
    private List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtos) {
        if (CollectionUtils.isEmpty(aclDtos)) {
            return Lists.newArrayList();
        }
        // 获取当前的权限模块树
        List<AclModuleLevelDto> aclModuleLevelList = aclModuleTree();

        // map集合 aclModuleId --> [aclDto1, aclDto2...]
        Multimap<Integer, AclDto> moduleIdAclMap = ArrayListMultimap.create();
        // 进行遍历
        for (AclDto aclDto : aclDtos) {
            if (aclDto.getStatus() == 1) {
                moduleIdAclMap.put(aclDto.getAclModuleId(), aclDto);
            }
        }

        // 进行绑定
        bindAclsWithOrder(aclModuleLevelList, moduleIdAclMap);
        return aclModuleLevelList;
    }

    /**
     * @description 将权限和权限模块进行绑定
     * @param [aclModuleLevelList, moduleIdAclMap]
     * @date 2019/7/23 8:33
     **/
    private void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelList,
                                   Multimap<Integer, AclDto> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelList)) {
            return ;
        }

        // 遍历权限模块
        for (AclModuleLevelDto dto : aclModuleLevelList) {
            // 获取该层的权限
            List<AclDto> aclDtos = (List<AclDto>) moduleIdAclMap.get(dto.getId());
            if (CollectionUtils.isNotEmpty(aclDtos)) {
                // 进行排序
                Collections.sort(aclDtos, aclDtoComparator);
                dto.setAclList(aclDtos);
            }
            // 进入下一层权限模块
            bindAclsWithOrder(dto.getAclModuleList(), moduleIdAclMap);
        }
    }

    /**
     * 权限比较器
     * */
    private Comparator<AclDto> aclDtoComparator = new Comparator<AclDto>() {
        @Override
        public int compare(AclDto o1, AclDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    /**
     * @description 获取权限模块树
     * @param []
     * @date 2019/7/20 15:50
     * @return java.util.List<com.duanxin.dto.AclModuleLevelDto>
     **/
    public List<AclModuleLevelDto> aclModuleTree() {
        List<SysAclModule> aclModuleList = sysAclModuleMapper.getAclModuleList();

        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        for (SysAclModule module : aclModuleList) {
            AclModuleLevelDto adapt = AclModuleLevelDto.adapt(module);
            dtoList.add(adapt);
        }

        return aclModuleToTree(dtoList);
    }

    /**
     * @description 将权限模块集合转化为树
     * @param [aclModuleLevelList]
     * @date 2019/7/21 7:45
     * @return java.util.List<com.duanxin.dto.AclModuleLevelDto>
     **/
    private List<AclModuleLevelDto> aclModuleToTree(List<AclModuleLevelDto> aclModuleLevelList) {
        if (CollectionUtils.isEmpty(aclModuleLevelList)) {
            return Lists.newArrayList();
        }

        // level --》[aclModule1,aclModule2,aclModule3...]
        Multimap<String, AclModuleLevelDto> levelDtoMap = ArrayListMultimap.create();
        List<AclModuleLevelDto> rootList = Lists.newArrayList();
        // 遍历原始结合
        for (AclModuleLevelDto dto : aclModuleLevelList) {
            levelDtoMap.put(dto.getLevel(), dto);
            if (StringUtils.equals(dto.getLevel(), LevelUtil.ROOT)) {
                rootList.add(dto);
            }
        }

        // 对根模块按seq从小到大排序
        Collections.sort(rootList, aclModuleComparator);

        // 对非根模块进行递归排序
        transformAclModuleLevel(rootList, LevelUtil.ROOT, levelDtoMap);
        return rootList;
    }

    /**
     * @description 递归处理子模块
     * @param [aclModuleLevelDtos, level, levelDtoMap]
     * @date 2019/7/21 7:46
     **/
    private void transformAclModuleLevel(List<AclModuleLevelDto> aclModuleLevelDtos,
                                         String level,
                                         Multimap<String, AclModuleLevelDto> levelDtoMap) {
        for (int i = 0; i < aclModuleLevelDtos.size(); ++i) {
            // 遍历本层
            AclModuleLevelDto aclModuleLevelDto = aclModuleLevelDtos.get(i);
            // 处理当前层数据
            String nextLevel = LevelUtil.calculateLevel(level, aclModuleLevelDto.getId());
            // 处理下一层
            List<AclModuleLevelDto> nextLevelDtos = (List<AclModuleLevelDto>) levelDtoMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(nextLevelDtos)) {
                // 对本层进行排序
                Collections.sort(nextLevelDtos, aclModuleComparator);
                // 获取下一层数据
                aclModuleLevelDto.setAclModuleList(nextLevelDtos);
                // 进入下一层
                transformAclModuleLevel(nextLevelDtos, nextLevel, levelDtoMap);
            }
        }
    }

    /**
     * 权限模块比较器
     * */
    private Comparator<AclModuleLevelDto> aclModuleComparator = new Comparator<AclModuleLevelDto>() {
        @Override
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    } ;

    /**
     * @description 获取部门树
     * @date 2019/7/18 7:28
     * @return java.util.List<com.duanxin.dto.DeptLevelDto>
     **/
    public List<DeptLevelDto> deptTree() {
        List<SysDept> deptList = sysDeptMapper.getDeptList();

        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for (SysDept dept : deptList) {
            DeptLevelDto adapt = DeptLevelDto.adapt(dept);
            dtoList.add(adapt);
        }
        return deptListToTree(dtoList);
    }

    /**
     * @description 将部门集合转化为树结构
     * @param [deptLevelList]
     * @date 2019/7/18 7:50
     * @return java.util.List<com.duanxin.dto.DeptLevelDto>
     **/
    private List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList) {
        if (CollectionUtils.isEmpty(deptLevelList)) {
            return Lists.newArrayList();
        }

        // level--》[dept1,dept2,....]
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootDeptList = Lists.newArrayList();

        // 进行遍历获取根部门
        for (DeptLevelDto dept : deptLevelList) {
            levelDeptMap.put(dept.getLevel(), dept);
            if(dept.getLevel().equals(LevelUtil.ROOT)) {
                rootDeptList.add(dept);
            }
        }
        // 将根部门按seq从大到小进行排序
        Collections.sort(rootDeptList, deptSeqComparator);

        // 递归生成树
        transformDeptTree(deptLevelList, LevelUtil.ROOT, levelDeptMap);
        return rootDeptList;
    }

    /**
     * @description 将除root级的部门进行排序
     * @param [deptLevelList, level, levelDeptMap]
     * @date 2019/7/18 8:00
     **/
    private void transformDeptTree(List<DeptLevelDto> deptLevelList, String level,
                                   Multimap<String, DeptLevelDto> levelDeptMap) {
        for (int i = 0; i < deptLevelList.size(); ++i) {
            // 遍历当前层元素
            DeptLevelDto deptLevelDto = deptLevelList.get(i);
            // 处理当前层的数据
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            // 处理下一层
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                // 排序
                Collections.sort(tempDeptList, deptSeqComparator);
                // 获取下一层数据
                deptLevelDto.setDeptList(tempDeptList);
                // 进入下一层处理
                transformDeptTree(tempDeptList, nextLevel, levelDeptMap);
            }
        }
    }

    /**
     * 部门次序比较器
     * */
    private Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
}
