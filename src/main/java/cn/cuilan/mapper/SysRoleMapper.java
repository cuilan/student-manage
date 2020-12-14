package cn.cuilan.mapper;

import cn.cuilan.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统角色mapper
 *
 * @author zhang.yan
 * @date 2020/12/14
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询所有系统角色
     *
     * @return 角色list
     */
    @Select("select * from t_sys_role")
    List<SysRole> getAllSysRole();

}
