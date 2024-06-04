package com.hp.common.base.model;

import com.google.common.base.Preconditions;
import com.hp.common.base.annotation.Trim;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import java.util.List;


/**
 * @author hp
 */
@Data
@Validated
public class PageRequestWrapper<T extends Request> {

    @Min(value = 1, message = "the page index of the page to be returned, must be greater than 0.")
    private Integer page = 1;

    @Min(value = 1, message = "the size of the page to be returned, must be greater than 0.")
    private Integer size = 10;

    @Trim
    private T queryParams;

    private List<OrderColumn> sorts;

    public void setPage(Integer page) {
        Preconditions.checkArgument(page != null && page > 0, "页码异常");
        this.page = page;
    }

    public void setSize(Integer size) {
        Preconditions.checkArgument(size != null && size > 0, "每页记录数异常");
        this.size = size;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderColumn {
        private String columnName;
        private Order sortedOrder;
    }

    public static OrderColumn desc(String columnName) {
        return new OrderColumn(columnName, Order.desc);
    }

    public static OrderColumn asc(String columnName) {
        return new OrderColumn(columnName, Order.asc);
    }

    public enum Order {
        /**
         * 排序
         */
        asc,
        desc,
        ;

        public boolean isAsc() {
            return asc.equals(this);
        }

        public boolean isDesc() {
            return !isAsc();
        }
    }
}
