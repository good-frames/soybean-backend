package com.soybean.upms.api.vo;

import lombok.Data;

import java.util.List;

/**
 * 用户菜单VO（包含路由和首页信息）
 *
 * @author soybean
 */
@Data
public class UserRouteResultVO {

    /**
     * 菜单路由列表
     */
    private List<RouteTreeVO> routes;

    /**
     * 首页路径
     */
    private String home;
}
