package cn.cuilan.mapper;

import cn.cuilan.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统菜单mapper
 *
 * @author zhang.yan
 * @date 2020/12/12
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 查询所有系统菜单
     *
     * @return 菜单list
     */
    @Select("select * from t_sys_menu where visible = true")
    List<SysMenu> getAllSysMenu();

}
