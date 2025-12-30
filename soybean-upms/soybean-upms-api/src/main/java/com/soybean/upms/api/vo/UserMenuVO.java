package com.soybean.upms.api.vo;

import com.soybean.upms.api.vo.MenuTreeVO;
import lombok.Data;

import java.util.List;

/**
 * 用户菜单VO（包含路由和首页信息）
 *
 * @author soybean
 */
@Data
public class UserMenuVO {

    /**
     * 菜单路由列表
     */
    private List<MenuTreeVO> routes;

    /**
     * 首页路径
     */
    private String home = "home";
}
