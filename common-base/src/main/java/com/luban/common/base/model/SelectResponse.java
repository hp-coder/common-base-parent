package com.luban.common.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectResponse implements Response{

    private Object value;
    private String label;
    private boolean disabled;

}
