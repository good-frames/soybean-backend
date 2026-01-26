package com.soybean.upms.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 用户菜单VO（包含路由和首页信息）
 *
 * @author soybean
 */
@Schema(description = "用户路由结果视图对象")
@Data
public class UserRouteResultVO {

    /**
     * 菜单路由列表
     */
    @Schema(description = "菜单路由列表")
    private List<RouteTreeVO> routes;

    /**
     * 首页路径
     */
    @Schema(description = "首页路径")
    private String home;
}
