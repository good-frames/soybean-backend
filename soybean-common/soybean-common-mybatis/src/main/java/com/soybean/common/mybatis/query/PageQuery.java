package com.soybean.common.mybatis.query;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * 分页查询基类
 *
 * @author soybean
 */
@Data
public class PageQuery {

    /**
     * 页码
     */
    private Integer pageNo;

    /**
     * 每页数据条数
     */
    private Integer pageSize;

    /**
     * 排序字段
     */
    private String sortBy;

    /**
     * 是否升序
     */
    private Boolean isAsc;
    
    /**
     * 转换为 MyBatis Plus 的 Page 对象
     *
     * @param orders 手动指定的排序字段
     * @param <T>   实体类型
     * @return MyBatis Plus 的 Page 对象
     */
    public <T> Page<T> toMpPage(OrderItem... orders) {
        // 设置默认值
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        // 1.分页条件
        Page<T> p = Page.of(pageNo, pageSize);
        // 2.排序条件
        // 2.1.先看前端有没有传排序字段
        if (sortBy != null) {
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(sortBy);
            orderItem.setAsc(isAsc != null ? isAsc : true);
            p.addOrder(orderItem);
            return p;
        }
        // 2.2.再看有没有手动指定排序字段
        if (orders != null && orders.length > 0) {
            p.addOrder(orders);
        }
        return p;
    }
    
    /**
     * 转换为 MyBatis Plus 的 Page 对象，并指定默认排序字段
     *
     * @param defaultSortBy 默认排序字段
     * @param isAsc         是否升序
     * @param <T>           实体类型
     * @return MyBatis Plus 的 Page 对象
     */
    public <T> Page<T> toMpPage(String defaultSortBy, boolean isAsc) {
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn(defaultSortBy);
        orderItem.setAsc(isAsc);
        return this.toMpPage(orderItem);
    }
    
    /**
     * 转换为 MyBatis Plus 的 Page 对象，并按创建时间倒序排序
     *
     * @param <T> 实体类型
     * @return MyBatis Plus 的 Page 对象
     */
    public <T> Page<T> toMpPageDefaultSortByCreateTimeDesc() {
        return toMpPage("create_time", false);
    }
    
    /**
     * 转换为 MyBatis Plus 的 Page 对象，并按更新时间倒序排序
     *
     * @param <T> 实体类型
     * @return MyBatis Plus 的 Page 对象
     */
    public <T> Page<T> toMpPageDefaultSortByUpdateTimeDesc() {
        return toMpPage("update_time", false);
    }

    /**
     * 转换为 MyBatis Plus 的 Page 对象，并按创建时间升序排序
     *
     * @param <T> 实体类型
     * @return MyBatis Plus 的 Page 对象
     */
    public <T> Page<T> toMpPageDefaultSortByCreateTimeAsc() {
        return toMpPage("create_time", true);
    }

    /**
     * 转换为 MyBatis Plus 的 Page 对象，并按更新时间升序排序
     *
     * @param <T> 实体类型
     * @return MyBatis Plus 的 Page 对象
     */
    public <T> Page<T> toMpPageDefaultSortByUpdateTimeAsc() {
        return toMpPage("update_time", true);
    }
}
