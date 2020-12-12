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
     * 根据用户名查询系统用户
     *
     * @param username 用户名称
     * @return 返回系统用户
     */
    @Select("SELECT * FROM t_sys_user WHERE username = #{username}")
    SysUser getByUsername(@Param("username") String username);

    /**
     * 根据用户名称搜索用户
     *
     * @param username 用户名称
     * @param pageNum  页码
     * @param pageSize 分页大小
     * @return 返回用户列表
     */
    @Select({"<script>",
            "SELECT * FROM t_sys_user WHERE 1 = 1 ",
            "<if test='username!=null'>",
            " and username like concat('%', #{username}, '%') ",
            "</if>",
            "</script>"})
    Page<SysUser> getListByUsername(@Param("username") String username,
                                @Param("pageNum") int pageNum,
                                @Param("pageSize") int pageSize);
}
