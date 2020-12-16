package cn.cuilan.mapper;

import cn.cuilan.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 系统用户mapper
 *
 * @author zhang.yan
 * @date 2020/12/12
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询系统用户
     */
    String QUERY_SYS_USER = "select * from t_sys_user ";

    /**
     * 查询用户及该用户的角色id、角色名称
     */
    String QUERY_SYS_USER_AND_ROLE = "select su.*, sr.id as roleId, sr.name as roleName, sr.description " +
            " from t_sys_user as su " +
            " left join t_sys_user_roles as sur on su.id = sur.sys_user_id " +
            " left join t_sys_role as sr on sr.id = sur.roles_id ";

    /**
     * 根据用户名查询系统用户
     *
     * @param username 用户名称
     * @return 返回系统用户
     */
    @Select(QUERY_SYS_USER + " WHERE username = #{username} ")
    SysUser getByUsername(@Param("username") String username);

    /**
     * 根据用户id查询
     *
     * @param sysUserId 系统用户id
     * @return 返回系统用户
     */
    @Select(QUERY_SYS_USER_AND_ROLE + " WHERE su.id = #{sysUserId}")
    SysUser getById(@Param("sysUserId") Long sysUserId);

    /**
     * 根据用户名称搜索用户
     *
     * @param username 用户名称
     * @param pageNum  页码
     * @param pageSize 分页大小
     * @return 返回用户列表
     */
    @Select({"<script>",
            QUERY_SYS_USER_AND_ROLE,
            " WHERE 1 = 1 ",
            "<if test='username!=null'>",
            " and su.username like concat('%', #{username}, '%') ",
            "</if>",
            "</script>"})
    Page<SysUser> getListByUsername(@Param("username") String username,
                                    @Param("pageNum") int pageNum,
                                    @Param("pageSize") int pageSize);
}
